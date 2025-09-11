package pl.allblue.ablibs.abapi;

class ABApiResult_Base {
    private int result;
    private String message;

    public ABApiResult_Base(int result, String message)
    {
        this.result = result;
        this.message = message;
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
}
