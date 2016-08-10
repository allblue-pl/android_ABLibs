package pl.allblue.api;

import android.util.Log;

import org.json.JSONObject;

import java.util.Map;

import pl.allblue.network.HttpRequest;


public class Api
{

    static public void Post(final String uri, final Map<String, String> fields,
            final ResponseListener response_listener, String id)
    {
        HttpRequest.Post(uri, fields, new HttpRequest.ResponseListener() {
            @Override
            public void onResponseReceived(HttpRequest.Response response,
                    String id) {
                response_listener.onResponseReceived(Api.ParseHttpResponse(
                        uri, response), id);
            }
        }, id);
    }

    static public void Post(final String uri, final Map<String, String> fields,
            final ResponseListener response_listener)
    {
        Api.Post(uri, fields, response_listener, null);
    }

    static public Result ParseHttpResponse(String uri,
            HttpRequest.Response http_response)
    {
        if (!http_response.isSuccess()) {
            Log.d("Api", "Http Result Error (" + uri + "): " +
                    http_response.getErrorMessage());

            return new Result(2,
                "Http Result Error: " + http_response.getErrorMessage(),
                new JSONObject());
        }

        int result = 2;
        String message = null;
        JSONObject json = null;

        try {
            json = new JSONObject(http_response.getData());

            result = json.getInt("result");
            message = json.getString("message");

            if (json.has("EDEBUG")) {
                Log.w("Api", "EDEBUG: " + json.getJSONArray("EDEBUG").toString());
            }
        } catch (Exception e) {
            json = new JSONObject();
            result = 2;
            message = "Cannot parse json data: " + http_response.getData();
        }

        Result api_result = new Result(result, message, json);
        if (!api_result.isSuccess())
            Log.d("Api", "Failure/Error on `" + uri + "`: " + message);

        return api_result;
    }

    static public class Result
    {

        private int result;
        private String message;
        private JSONObject json;

        public Result(int result, String message, JSONObject json)
        {
            this.result = result;
            this.message = message;
            this.json = json;
        }

        public boolean isError()
        {
            return !this.isSuccess() && !this.isFailure();
        }

        public boolean isFailure()
        {
            return this.result == 1;
        }

        public boolean isSuccess()
        {
            return this.result == 0;
        }

        public String getMessage()
        {
            return this.message;
        }

        public JSONObject getData()
        {
            return this.json;
        }

    }

    public interface ResponseListener
    {
        void onResponseReceived(Result result, String id);
    }

}
