using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SafeAround.Api.Dto;
using SafeAround.Api.Persistence;

namespace SafeAround.Api.Controllers;

[Route("/[controller]")]
public class CategoryController : ControllerBase
{
    private readonly SafeAroundDbContext _dbContext;
    
    public CategoryController(SafeAroundDbContext dbContext)
    {
        _dbContext = dbContext;
    }
    
    [HttpGet]
    public async Task<IActionResult> GetCategories()
    {
        var categories = await _dbContext.IncidentCategories
            .Select(c => new GetCategoryResponse
            {
                Id = c.Id,
                Name = c.Name,
                IconCode = c.Code,
            }).ToListAsync();

        return Ok(categories);
    }
}