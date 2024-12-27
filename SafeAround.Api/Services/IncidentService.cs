using FluentResults;
using Microsoft.EntityFrameworkCore;
using SafeAround.Api.Dto;
using SafeAround.Api.Helpers;
using SafeAround.Api.Models;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Entities;

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
                OccurrenceDate = i.CreatedOn,
                CategoryId = i.CategoryId,
                CategoryName = i.Category.Name,
                CategoryCode = i.Category.Code,
                UserId = i.User.Id,
                DistanceInKm = null
            }).FirstOrDefaultAsync();
    }
    
    public async Task<ApiResponse> AddAsync(AddIncidentRequest request, Guid userId)
    {
        //TODO: Remove this line when authentication is implemented
        userId = _dbContext.Users.First().Id;

        bool categoryExists = await _dbContext.IncidentCategories.AnyAsync(c => c.Id == request.CategoryId);
        if (!categoryExists)
        {
            return ApiResponse.Fail("Category not found");
        }

        var incident = new Incident
        {
            Title = request.Title,
            Description = request.Description,
            Latitude = request.Latitude,
            Longitude = request.Longitude,
            UserId = userId,
            CategoryId = request.CategoryId
        };

        _dbContext.Add(incident);
        await _dbContext.SaveChangesAsync();

        return ApiResponse.Success("Incident added successfully");
    }

    public async Task<List<GetIncidentResponse>> GetIncidentsAroundAsync(GetIncidentsAroundRequest request)
    {
        var requestPoint = new Point(request.Latitude, request.Longitude);
        var radiusInKm = request.RadiusUnit switch
        {
            RadiusUnit.Miles => request.Radius * Constants.KilometersInMile,
            RadiusUnit.Kilometers => request.Radius,
            null => request.Radius,
            _ => throw new ArgumentOutOfRangeException()
        };

        var incidents = await _dbContext.Incidents
            .Select(i => new GetIncidentResponse
            {
                Id = i.Id,
                Title = i.Title,
                Description = i.Description,
                Latitude = i.Latitude,
                Longitude = i.Longitude,
                OccurrenceDate = i.CreatedOn,
                CategoryId = i.CategoryId,
                CategoryName = i.Category.Name,
                CategoryCode = i.Category.Code,
                UserId = i.User.Id,
                DistanceInKm = _dbContext.DistanceBetweenPoints(requestPoint.Latitude, requestPoint.Longitude, i.Latitude, i.Longitude) / 1000,
            })
            .Where(i => i.DistanceInKm <= radiusInKm)
            .OrderBy(i => i.DistanceInKm)
            .ToListAsync();

        foreach (var incident in incidents)
        {
            incident.DistanceInKm = (float)Math.Round(incident.DistanceInKm!.Value, 3, MidpointRounding.AwayFromZero);
        }

        return incidents;
    }
}