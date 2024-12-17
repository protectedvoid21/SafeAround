using FluentResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SafeAround.Api.Dto;
using SafeAround.Api.Persistence;
using SafeAround.Api.Services;

namespace SafeAround.Api.Endpoints;

public class CategoryApi : IEndpointMap
{
    public void MapEndpoints(IEndpointRouteBuilder routes)
    {
        var group = routes.MapGroup("/categories");
        
        group.MapGet("/", async ([FromServices] SafeAroundDbContext _dbContext) =>
        {
            var categories = await _dbContext.IncidentCategories
                .Select(c => new GetCategoryResponse
                {
                    Id = c.Id,
                    Name = c.Name,
                    IconCode = c.Code,
                }).ToListAsync();

            return Results.Ok(categories);
        });
    }
}