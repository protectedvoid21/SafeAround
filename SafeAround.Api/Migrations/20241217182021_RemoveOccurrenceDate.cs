using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SafeAround.Api.Migrations
{
    /// <inheritdoc />
    public partial class RemoveOccurrenceDate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "occurrence_date",
                table: "incidents");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "occurrence_date",
                table: "incidents",
                type: "timestamp without time zone",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));
        }
    }
}
