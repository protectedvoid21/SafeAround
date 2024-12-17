using System.Text.RegularExpressions;

namespace SafeAround.Api.Helpers;

public class SlugifyParameterTransformer : IOutboundParameterTransformer
{
    public string? TransformOutbound(object? value)
    {
        if (value == null) { return null; }

        return Regex.Replace(value.ToString()!, "([a-z])([A-Z])", "$1_$2").ToLower();
    }
}