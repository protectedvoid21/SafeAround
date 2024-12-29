using FluentValidation;

namespace SafeAround.Api.Dto;

public class GetIncidentsAroundRequest
{
    public required float Latitude { get; set; }
    public required float Longitude { get; set; }
    public required float Radius { get; set; }
    public RadiusUnit? RadiusUnit { get; set; } = Dto.RadiusUnit.Kilometers;
}

public class GetIncidentsAroundRequestValidator : AbstractValidator<GetIncidentsAroundRequest>
{
    public GetIncidentsAroundRequestValidator()
    {
        RuleFor(x => x.Latitude).InclusiveBetween(-90, 90);
        RuleFor(x => x.Longitude).InclusiveBetween(-180, 180);
        RuleFor(x => x.Radius).GreaterThan(0);
    }
}