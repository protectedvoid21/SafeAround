namespace SafeAround.Api.Persistence.Entities;

public abstract class BaseTrackingEntity : BaseEntity
{
    public DateTime CreatedOn { get; set; }
    public DateTime ModifiedOn { get; set; }
}