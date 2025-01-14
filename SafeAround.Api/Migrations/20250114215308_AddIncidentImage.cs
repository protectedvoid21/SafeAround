using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace SafeAround.Api.Migrations
{
    /// <inheritdoc />
    public partial class AddIncidentImage : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "incident_comments",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    content = table.Column<string>(type: "text", nullable: false),
                    user_id = table.Column<Guid>(type: "uuid", nullable: false),
                    incident_id = table.Column<int>(type: "integer", nullable: false),
                    created_on = table.Column<DateTime>(type: "timestamp without time zone", nullable: false),
                    modified_on = table.Column<DateTime>(type: "timestamp without time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("pk_incident_comments", x => x.id);
                    table.ForeignKey(
                        name: "fk_incident_comments_incidents_incident_id",
                        column: x => x.incident_id,
                        principalTable: "incidents",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "fk_incident_comments_users_user_id",
                        column: x => x.user_id,
                        principalTable: "AspNetUsers",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "incident_images",
                columns: table => new
                {
                    id = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    file_name = table.Column<string>(type: "character varying(200)", maxLength: 200, nullable: false),
                    incident_id = table.Column<int>(type: "integer", nullable: false),
                    created_on = table.Column<DateTime>(type: "timestamp without time zone", nullable: false),
                    modified_on = table.Column<DateTime>(type: "timestamp without time zone", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("pk_incident_images", x => x.id);
                    table.ForeignKey(
                        name: "fk_incident_images_incidents_incident_id",
                        column: x => x.incident_id,
                        principalTable: "incidents",
                        principalColumn: "id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "ix_incident_comments_incident_id",
                table: "incident_comments",
                column: "incident_id");

            migrationBuilder.CreateIndex(
                name: "ix_incident_comments_user_id",
                table: "incident_comments",
                column: "user_id");

            migrationBuilder.CreateIndex(
                name: "ix_incident_images_incident_id",
                table: "incident_images",
                column: "incident_id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "incident_comments");

            migrationBuilder.DropTable(
                name: "incident_images");
        }
    }
}
