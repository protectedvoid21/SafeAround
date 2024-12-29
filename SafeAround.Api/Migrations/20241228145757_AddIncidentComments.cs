using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SafeAround.Api.Migrations
{
    /// <inheritdoc />
    public partial class AddIncidentComments : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "fk_incident_comment_incidents_incident_id",
                table: "incident_comment");

            migrationBuilder.DropForeignKey(
                name: "fk_incident_comment_users_user_id",
                table: "incident_comment");

            migrationBuilder.DropPrimaryKey(
                name: "pk_incident_comment",
                table: "incident_comment");

            migrationBuilder.RenameTable(
                name: "incident_comment",
                newName: "incident_comments");

            migrationBuilder.RenameIndex(
                name: "ix_incident_comment_user_id",
                table: "incident_comments",
                newName: "ix_incident_comments_user_id");

            migrationBuilder.RenameIndex(
                name: "ix_incident_comment_incident_id",
                table: "incident_comments",
                newName: "ix_incident_comments_incident_id");

            migrationBuilder.AddPrimaryKey(
                name: "pk_incident_comments",
                table: "incident_comments",
                column: "id");

            migrationBuilder.AddForeignKey(
                name: "fk_incident_comments_incidents_incident_id",
                table: "incident_comments",
                column: "incident_id",
                principalTable: "incidents",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "fk_incident_comments_users_user_id",
                table: "incident_comments",
                column: "user_id",
                principalTable: "AspNetUsers",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "fk_incident_comments_incidents_incident_id",
                table: "incident_comments");

            migrationBuilder.DropForeignKey(
                name: "fk_incident_comments_users_user_id",
                table: "incident_comments");

            migrationBuilder.DropPrimaryKey(
                name: "pk_incident_comments",
                table: "incident_comments");

            migrationBuilder.RenameTable(
                name: "incident_comments",
                newName: "incident_comment");

            migrationBuilder.RenameIndex(
                name: "ix_incident_comments_user_id",
                table: "incident_comment",
                newName: "ix_incident_comment_user_id");

            migrationBuilder.RenameIndex(
                name: "ix_incident_comments_incident_id",
                table: "incident_comment",
                newName: "ix_incident_comment_incident_id");

            migrationBuilder.AddPrimaryKey(
                name: "pk_incident_comment",
                table: "incident_comment",
                column: "id");

            migrationBuilder.AddForeignKey(
                name: "fk_incident_comment_incidents_incident_id",
                table: "incident_comment",
                column: "incident_id",
                principalTable: "incidents",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "fk_incident_comment_users_user_id",
                table: "incident_comment",
                column: "user_id",
                principalTable: "AspNetUsers",
                principalColumn: "id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
