using Microsoft.AspNetCore.Mvc;
using SafeAround.Api.Dto;
using SafeAround.Api.Models;
using SafeAround.Api.Services;

namespace SafeAround.Api.Controllers;

[ApiController]
[Route("/[controller]")]
public class IncidentController : ControllerBase
{
    private readonly IncidentService _incidentService;

    public IncidentController(IncidentService incidentService)
    {
        _incidentService = incidentService;
    }

    [HttpGet]
    public async Task<IActionResult> GetAll()
    {
        var incidents = await _incidentService.GetAllAsync();
        return Ok(incidents);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetById(int id)
    {
        var incident = await _incidentService.GetByIdAsync(id);
        return Ok(incident);
    }

    [HttpPost]
    public async Task<IActionResult> Add([FromBody] AddIncidentRequest incidentRequest)
    {
        ApiResponse result = await _incidentService.AddAsync(incidentRequest, Guid.NewGuid());
        return Ok(result);
    }
    
    [HttpGet("around")]
    public async Task<IActionResult> GetAround([FromQuery] GetIncidentsAroundRequest request)
    {
        var incidents = await _incidentService.GetIncidentsAroundAsync(request);
        return Ok(incidents);
    }
}