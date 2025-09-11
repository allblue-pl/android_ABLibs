package pl.allblue.ablibs.abapi;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

public class ABApiResult_Stream extends ABApiResult_Base {
    private BufferedReader bufferedReader = null;
    private HttpURLConnection urlConnection = null;

    public ABApiResult_Stream(int result, String message,
            BufferedReader bufferedReader,
            HttpURLConnection urlConnection) {
        super(result, message);

        this.bufferedReader = bufferedReader;
        this.urlConnection = urlConnection;
    }

    public BufferedReader getBufferedReader() {
        return this.bufferedReader;
    }

    public HttpURLConnection getURLConnection() {
        return this.urlConnection;
    }

    public ABApi.Stream_Chunk readChunk() {
        return ABApi.Stream_ReadChunk(this.bufferedReader);
    }
}
