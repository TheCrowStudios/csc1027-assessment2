"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
console.log("edit_artifact.ts active");
const editable = {
    ccell: null,
    cval: null,
    /**
     * Start editing a table cell.
     * @param {HTMLElement} cell The table cell to edit.
     */
    edit: (cell) => {
        editable.ccell = cell;
        editable.cval = cell.innerHTML;
        cell.classList.add("edit");
        cell.contentEditable = "true";
        cell.focus();
        cell.onblur = () => editable.done();
        cell.onkeydown = (e) => {
            if (e.key === "Enter") {
                editable.done();
            }
            if (e.key === "Escape") {
                editable.done(true);
            }
        };
    },
    /**
     * Complete editing the cell, either saving or discarding changes.
     * @param {boolean} discard Whether to discard the changes or not.
     */
    done: (discard = false) => {
        var _a;
        if (editable.ccell) {
            editable.ccell.onblur = null;
            editable.ccell.onkeydown = null;
            editable.ccell.classList.remove("edit");
            editable.ccell.contentEditable = "false";
            if (discard) {
                editable.ccell.innerHTML = (_a = editable.cval) !== null && _a !== void 0 ? _a : "";
            }
            if (editable.ccell.innerHTML !== editable.cval) {
                console.log(editable.ccell.innerHTML);
            }
        }
    }
};
// Wait for the window to load before initializing the editable cells
window.addEventListener("load", () => {
    const tableCells = document.querySelectorAll(".table-editable td");
    tableCells.forEach((td) => {
        if (!td.innerHTML.includes('select'))
            td.addEventListener("click", () => editable.edit(td));
    });
});
// Add a row to the table when the button is clicked
const table = document.getElementById("table-editable");
const btnAddRow = document.getElementById("btn-add-row");
btnAddRow.addEventListener("click", () => {
    const newRow = table.insertRow();
    for (let i = 0; i < 5; i++) {
        const newCell = newRow.insertCell(i);
        if (!newCell.innerHTML.includes('select'))
            newCell.addEventListener("click", () => editable.edit(newCell));
    }
    console.log("row added");
});
// Function to fetch data from the server and populate the table
function fetchDataAndFillTable(url) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const response = yield fetch(url);
            if (!response.ok) {
                throw new Error("Failed to fetch data");
            }
            const data = yield response.json();
            const tableBody = table.querySelector("tbody");
            if (tableBody) {
                // Clear existing rows in the table
                tableBody.innerHTML = "";
                // Fill table with rows from the data
                data.forEach((item) => {
                    const row = tableBody.insertRow();
                    // Assuming the data has 5 columns (adjust as needed)
                    let jsonObject = item;
                    const idCell = row.insertCell();
                    const imageCell = row.insertCell();
                    const nameCell = row.insertCell();
                    const descriptionCell = row.insertCell();
                    const engagementTimeCell = row.insertCell();
                    idCell.innerHTML = jsonObject.id;
                    nameCell.innerHTML = jsonObject.name;
                    descriptionCell.innerHTML = jsonObject.description;
                    engagementTimeCell.innerHTML = jsonObject.engagementTime;
                    /*
                    Object.values(item).slice(0, 5).forEach((value) => {
                        const newCell = row.insertCell();
                        newCell.innerHTML = value;
                        newCell.addEventListener("click", () => editable.edit(newCell));
                    });
                    */
                });
            }
        }
        catch (error) {
            console.error("Error fetching data:", error);
        }
    });
}
// Example: Fetch data from a URL and fill the table
fetchDataAndFillTable("http://localhost:8888/artifacts.json");
