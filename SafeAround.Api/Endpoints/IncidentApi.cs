using Microsoft.AspNetCore.Mvc;
using SafeAround.Api.Dto;
using SafeAround.Api.Services;

namespace SafeAround.Api.Endpoints;

public class IncidentApi : IEndpointMap
{
    public void MapEndpoints(IEndpointRouteBuilder routes)
    {
        var group = routes.MapGroup("/incidents");
        
        group.MapGet("/", async ([FromServices] IncidentService incidentService) =>
        {
            return Results.Ok(await incidentService.GetAllAsync());
        });
        
        group.MapGet("/{id}", async ([FromServices] IncidentService incidentService, Guid id) =>
        {
            return Results.Ok(await incidentService.GetAllAsync());
        });
        
        group.MapPost("/", async ([FromServices] IncidentService incidentService, AddIncidentRequest incidentRequest) =>
        {
            var result = await incidentService.AddAsync(incidentRequest, Guid.NewGuid());
            return result.IsSuccess ? Results.Created($"/incidents", result) : Results.BadRequest(result);
        });
    }
}