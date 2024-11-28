var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
console.log("edit_artifact.ts active");
var editable = {
    ccell: null,
    cval: null,
    /**
     * Start editing a table cell.
     * @param {HTMLElement} cell The table cell to edit.
     */
    edit: function (cell) {
        editable.ccell = cell;
        editable.cval = cell.innerHTML;
        cell.classList.add("edit");
        cell.contentEditable = "true";
        cell.focus();
        cell.onblur = function () { return editable.done(); };
        cell.onkeydown = function (e) {
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
    done: function (discard) {
        var _a;
        if (discard === void 0) { discard = false; }
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
window.addEventListener("load", function () {
    var tableCells = document.querySelectorAll(".table-editable td");
    tableCells.forEach(function (td) {
        if (!td.innerHTML.includes('select'))
            td.addEventListener("click", function () { return editable.edit(td); });
    });
});
// Add a row to the table when the button is clicked
var table = document.getElementById("table-editable");
var btnAddRow = document.getElementById("btn-add-row");
btnAddRow.addEventListener("click", function () {
    var newRow = table.insertRow();
    var _loop_1 = function (i) {
        var newCell = newRow.insertCell(i);
        newCell.addEventListener("click", function () { return editable.edit(newCell); });
    };
    for (var i = 0; i < 5; i++) {
        _loop_1(i);
    }
    console.log("row added");
});
// Function to fetch data from the server and populate the table
function fetchDataAndFillTable(url) {
    return __awaiter(this, void 0, void 0, function () {
        var response, data, tableBody_1, error_1;
        return __generator(this, function (_a) {
            switch (_a.label) {
                case 0:
                    _a.trys.push([0, 3, , 4]);
                    return [4 /*yield*/, fetch(url)];
                case 1:
                    response = _a.sent();
                    if (!response.ok) {
                        throw new Error("Failed to fetch data");
                    }
                    return [4 /*yield*/, response.json()];
                case 2:
                    data = _a.sent();
                    tableBody_1 = table.querySelector("tbody");
                    if (tableBody_1) {
                        // Clear existing rows in the table
                        tableBody_1.innerHTML = "";
                        // Fill table with rows from the data
                        data.forEach(function (item) {
                            var row = tableBody_1.insertRow();
                            // Assuming the data has 5 columns (adjust as needed)
                            Object.values(item).slice(0, 5).forEach(function (value) {
                                var newCell = row.insertCell();
                                newCell.innerHTML = value;
                                newCell.addEventListener("click", function () { return editable.edit(newCell); });
                            });
                        });
                    }
                    return [3 /*break*/, 4];
                case 3:
                    error_1 = _a.sent();
                    console.error("Error fetching data:", error_1);
                    return [3 /*break*/, 4];
                case 4: return [2 /*return*/];
            }
        });
    });
}
// Example: Fetch data from a URL and fill the table
fetchDataAndFillTable("http://localhost:8888/artifacts.json");
