using SafeAround.Api.Persistence;

namespace SafeAround.Api.Endpoints;

public class IncidentApi : IEndpointMap
{
    public void MapEndpoints(IEndpointRouteBuilder routes)
    {
        var group = routes.MapGroup("/incidents");
        
        group.MapGet("/", async (SafeAroundDbContext dbContext) =>
        {
            return await dbContext.Incidents.ToListAsync();
        });

        return routes;
    }
}