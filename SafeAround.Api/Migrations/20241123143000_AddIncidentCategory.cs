using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace SafeAround.Api.Migrations
{
    /// <inheritdoc />
    public partial class AddIncidentCategory : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "category_id",
                table: "incidents",
                type: "integer",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "incident_categories",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    name = table.Column<string>(type: "text", nullable: false),
                    description = table.Column<string>(type: "text", nullable: false),
                    code = table.Column<string>(type: "text", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("pk_incident_categories", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "ix_incidents_category_id",
                table: "incidents",
                column: "category_id");

            migrationBuilder.AddForeignKey(
                name: "fk_incidents_incident_categories_category_id",
                table: "incidents",
                column: "category_id",
                principalTable: "incident_categories",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "fk_incidents_incident_categories_category_id",
                table: "incidents");

            migrationBuilder.DropTable(
                name: "incident_categories");

            migrationBuilder.DropIndex(
                name: "ix_incidents_category_id",
                table: "incidents");

            migrationBuilder.DropColumn(
                name: "category_id",
                table: "incidents");
        }
    }
}
