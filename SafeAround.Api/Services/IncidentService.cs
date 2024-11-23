using FluentResults;
using Microsoft.EntityFrameworkCore;
using SafeAround.Api.Dto;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Models;

namespace SafeAround.Api.Services;

public class IncidentService
{
    private readonly SafeAroundDbContext _dbContext;

    public IncidentService(SafeAroundDbContext dbContext)
    {
        _dbContext = dbContext;
    }
    
    public async Task<GetIncidentResponse?> GetByIdAsync(int id)
    {
        return await _dbContext.Incidents
            .Select(i => new GetIncidentResponse
            {
                Id = i.Id,
                Title = i.Title,
                Description = i.Description,
                Latitude = i.Latitude,
                Longitude = i.Longitude,
                OccurrenceDate = i.OccurrenceDate,
                CategoryId = i.CategoryId,
                CategoryName = i.Category.Name,
                CategoryCode = i.Category.Code,
                UserId = i.User.Id
            }).FirstOrDefaultAsync();
    }
    
    public async Task<List<GetIncidentResponse>> GetAllAsync()
    {
        return await _dbContext.Incidents.Select(i => new GetIncidentResponse
        {
            Id = i.Id,
            Title = i.Title,
            Description = i.Description,
            Latitude = i.Latitude,
            Longitude = i.Longitude,
            OccurrenceDate = i.OccurrenceDate,
            CategoryId = i.CategoryId,
            CategoryName = i.Category.Name,
            CategoryCode = i.Category.Code,
            UserId = i.User.Id
        }).ToListAsync();
    }

    public async Task<Result> AddAsync(AddIncidentRequest request, Guid userId)
    {
        var incident = new Incident
        {
            Title = request.Title,
            Description = request.Description,
            Latitude = request.Latitude,
            Longitude = request.Longitude,
            OccurrenceDate = request.Date,
            UserId = userId,
            CategoryId = request.CategoryId
        };

        _dbContext.Add(incident);
        await _dbContext.SaveChangesAsync();

        return Result.Ok();
    }
}