console.log("edit_artifact.ts active")

// Define the Editable object interface
interface Editable {
    ccell: HTMLElement | null;
    cval: string | null;
    edit: (cell: HTMLElement) => void;
    done: (discard?: boolean) => void;
}

const editable: Editable = {
    ccell: null,
    cval: null,

    /**
     * Start editing a table cell.
     * @param {HTMLElement} cell The table cell to edit.
     */
    edit: (cell: HTMLElement) => {
        editable.ccell = cell;
        editable.cval = cell.innerHTML;

        cell.classList.add("edit");
        cell.contentEditable = "true";
        cell.focus();

        cell.onblur = () => editable.done();
        cell.onkeydown = (e) => {
            if (e.key === "Enter") { editable.done(); }
            if (e.key === "Escape") { editable.done(true); }
        };
    },

    /**
     * Complete editing the cell, either saving or discarding changes.
     * @param {boolean} discard Whether to discard the changes or not.
     */
    done: (discard: boolean = false) => {
        if (editable.ccell) {
            editable.ccell.onblur = null;
            editable.ccell.onkeydown = null;
            editable.ccell.classList.remove("edit");
            editable.ccell.contentEditable = "false";

            if (discard) {
                editable.ccell.innerHTML = editable.cval ?? "";
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
        if (!td.innerHTML.includes('select')) td.addEventListener("click", () => editable.edit(td as HTMLElement));
    });
});

// Add a row to the table when the button is clicked
const table = document.getElementById("table-editable") as HTMLTableElement;
const btnAddRow = document.getElementById("btn-add-row") as HTMLElement;

btnAddRow.addEventListener("click", () => {
    const newRow = table.insertRow();
    for (let i = 0; i < 5; i++) {
        const newCell = newRow.insertCell(i);
        if (!newCell.innerHTML.includes('select')) newCell.addEventListener("click", () => editable.edit(newCell as HTMLElement));
    }
    console.log("row added");
});

// Function to fetch data from the server and populate the table
async function fetchDataAndFillTable(url: string): Promise<void> {
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Failed to fetch data");
        }

        const data = await response.json();

        const tableBody = table.querySelector("tbody");

        if (tableBody) {
            // Clear existing rows in the table
            tableBody.innerHTML = "";

            // Fill table with rows from the data
            data.forEach((item: any) => {
                const row = tableBody.insertRow();

                // Assuming the data has 5 columns (adjust as needed)
                Object.values(item).slice(0, 5).forEach((value) => {
                    const newCell = row.insertCell();
                    newCell.innerHTML = value;
                    newCell.addEventListener("click", () => editable.edit(newCell));
                });
            });
        }
    } catch (error) {
        console.error("Error fetching data:", error);
    }
}

// Example: Fetch data from a URL and fill the table
fetchDataAndFillTable("http://localhost:8888/artifacts.json");