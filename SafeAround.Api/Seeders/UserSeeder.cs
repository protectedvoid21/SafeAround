using Bogus;
using Microsoft.AspNetCore.Identity;
using SafeAround.Api.Persistence.Entities;

namespace SafeAround.Api.Seeders;

public class UserSeeder : ISeeder
{
    private readonly UserManager<AppUser> _userManager;
    
    public UserSeeder(UserManager<AppUser> userManager)
    {
        _userManager = userManager;
    }

    public void Seed()
    {
        if (_userManager.Users.Any())
        {
            return;
        }
        
        var faker = new Faker<AppUser>()
            .RuleFor(x => x.UserName, f => f.Internet.UserName())
            .RuleFor(x => x.Email, (f, u) => f.Internet.Email(u.UserName))
            .RuleFor(x => x.PhoneNumber, f => f.Phone.PhoneNumber());
        
        var users = faker.Generate(10);

        foreach (var user in users)
        {
            _userManager.CreateAsync(user, "Test1234!").Wait();
        }
    }
}