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
    
    public async Task<Incident?> GetByIdAsync(int id)
    {
        return await _dbContext.Incidents.FirstOrDefaultAsync(x => x.Id == id);
    }
    
    public async Task<List<Incident>> GetAllAsync()
    {
        return await _dbContext.Incidents.ToListAsync();
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
            UserId = userId
        };

        _dbContext.Add(incident);
        await _dbContext.SaveChangesAsync();

        return Result.Ok();
    }
}