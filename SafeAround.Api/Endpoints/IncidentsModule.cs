using Carter;
using Microsoft.AspNetCore.Mvc;
using SafeAround.Api.Dto;
using SafeAround.Api.Models;
using SafeAround.Api.Services;

namespace SafeAround.Api.Endpoints;

public class IncidentsModule : ICarterModule
{
    public void AddRoutes(IEndpointRouteBuilder app)
    {
        var group = app.MapGroup("/incidents");
        
        group.MapGet("/", async ([AsParameters] GetIncidentsAroundRequest request, IncidentService incidentService) =>
        {
            var validation = new GetIncidentsAroundRequestValidator().Validate(request);
            if (!validation.IsValid)
            {
                return Results.BadRequest(validation.Errors.Select(e => e.ErrorMessage));
            }
            List<GetIncidentResponse> incidents = await incidentService.GetIncidentsAroundAsync(request);
            return Results.Ok(incidents);
        });
        
        group.MapGet("/{id:int}", async (int id, IncidentService incidentService) =>
        {
            var incident = await incidentService.GetByIdAsync(id);
            return Results.Ok(incident);
        });
        
        group.MapPost("/", async (AddIncidentRequest incidentRequest, IncidentService incidentService) =>
        {
            ApiResponse result = await incidentService.AddAsync(incidentRequest, Guid.NewGuid());
            return Results.Ok(result);
        });
        
        var commentsGroup = group.MapGroup("/{incidentId:int}/comments");
        
        commentsGroup.MapPost("/", async ([FromRoute] int incidentId, [FromBody] string content, IncidentService incidentService) =>
        {
            ApiResponse result = await incidentService.AddCommentAsync(new AddCommentRequest(incidentId, content), Guid.NewGuid());
            return Results.Ok(result);
        });
    }
}