using SafeAround.Api.Endpoints;

namespace SafeAround.Api;

public static class ApplicationEndpoints
{
    private static readonly IEndpointMap[] Endpoints =
    [
        new IncidentApi(),
        new CategoryApi()
    ];
    
    public static void MapEndpoints(this IEndpointRouteBuilder routes)
    {
        foreach(var endpoint in Endpoints)
        {
            endpoint.MapEndpoints(routes);
        }
    }
}