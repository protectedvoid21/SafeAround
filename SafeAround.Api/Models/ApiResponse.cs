namespace SafeAround.Api.Models;

public class ApiResponse
{
    public bool IsSuccess { get; set; }
    public string Message { get; set; }
    
    public static ApiResponse Fail(string message)
    {
        return new ApiResponse
        {
            IsSuccess = false,
            Message = message
        };
    }
    
    public static ApiResponse Success()
    {
        return new ApiResponse
        {
            IsSuccess = true
        };
    }
    
    public static ApiResponse Success(string message)
    {
        return new ApiResponse
        {
            IsSuccess = true,
            Message = message
        };
    }
}

public class ApiResponse<T> : ApiResponse
{
    public T Data { get; set; }
    
    public static ApiResponse<T> Success(T data)
    {
        return new ApiResponse<T>
        {
            IsSuccess = true,
            Data = data
        };
    }
}