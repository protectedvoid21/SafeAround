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
            request.Radius = 9999;
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
        
        group.MapPost("/image/{incidentId:int}", async ([FromRoute] int incidentId, IFormFile request, IncidentService incidentService) =>
        {
            var uploadRequest = new UploadIncidentImageRequest
            {
                IncidentId = incidentId,
                FileName = request.FileName,
                ImageStream = request.OpenReadStream()
            };
            ApiResponse result = await incidentService.UploadImageAsync(uploadRequest);
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