using Microsoft.EntityFrameworkCore;
using SafeAround.Api.Dto;
using SafeAround.Api.Persistence;
using SafeAround.Api.Persistence.Entities;
using SafeAround.Api.Services;
using Shouldly;

namespace SafeAround.Api.Tests;

public class IncidentServiceTests
{
    private readonly SafeAroundDbContext _dbContext;
    private readonly IncidentService _incidentService;

    public IncidentServiceTests()
    {
        var options = new DbContextOptionsBuilder<SafeAroundDbContext>()
            .UseInMemoryDatabase(databaseName: "TestDatabase")
            .Options;
        _dbContext = new SafeAroundDbContext(options);
        _incidentService = new IncidentService(_dbContext);
    }
    
    [Fact]
    public async Task AddAsync_ShouldAddIncident_WhenCategoryExists()
    {
        // Arrange
        var category = new IncidentCategory
        {
            Id = 1,
            Name = "Test Category",
            Code = "TC",
            Description = "Test Description"
        };
        var user = new AppUser { Id = Guid.NewGuid(), UserName = "TestUser" };
        _dbContext.IncidentCategories.Add(category);
        _dbContext.Users.Add(user);
        await _dbContext.SaveChangesAsync();

        var request = new AddIncidentRequest
        {
            Title = "Test Incident",
            Description = "Test Description",
            Latitude = 50.0f,
            Longitude = 17.0f,
            CategoryId = category.Id
        };

        // Act
        var response = await _incidentService.AddAsync(request, user.Id);

        // Assert
        response.IsSuccess.ShouldBeTrue();
        var incident = await _dbContext.Incidents.FirstOrDefaultAsync(i => i.Title == request.Title);
        incident.ShouldNotBeNull();
        incident.Description.ShouldBe(request.Description);
        incident.Latitude.ShouldBe(request.Latitude);
        incident.Longitude.ShouldBe(request.Longitude);
        incident.CategoryId.ShouldBe(category.Id);
    }

    [Fact]
    public async Task AddAsync_ShouldFail_WhenCategoryDoesNotExist()
    {
        // Arrange
        var user = new AppUser { Id = Guid.NewGuid(), UserName = "TestUser" };
        _dbContext.Users.Add(user);
        await _dbContext.SaveChangesAsync();

        var request = new AddIncidentRequest
        {
            Title = "Test Incident",
            Description = "Test Description",
            Latitude = 50.0f,
            Longitude = 17.0f,
            CategoryId = 999 // Non-existent category
        };

        // Act
        var response = await _incidentService.AddAsync(request, user.Id);

        // Assert
        response.IsSuccess.ShouldBeFalse();
    }
}