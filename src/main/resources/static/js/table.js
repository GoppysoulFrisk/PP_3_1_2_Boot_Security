let tbl = "";
let mT = 0;
function mkTbl(tdSz, n) {
    mT = n;
    let td = "<td style = 'background-color:#f0f0f0; width:" + tdSz + "; height:" + tdSz + "'";
    td += " onclick = 'sayRC(this)'></td>";
    let tHdr = "<table id = 'tbl' style = 'background-color:#ccccaa; cursor:pointer'; border = 1px";
    document.write(tHdr);
    for (i = 0; i < mT; i++) {
        document.write("<tr>");
        for (j = 0; j < mT; j++) {
            document.write(td);
        }
        document.write("</tr>");
    }
    document.write("</table>");
    tbl = document.getElementById("tbl");
}
function sayRC(cll) {
    // Номер столбца текущей ячейки
    c = cll.cellIndex;
    r = gtRw(cll, c);
    alert("Ячейка " + r + ":" + c);
}
// Возвращает номер строки, которой расположена ячейка cll
function gtRw(cll, c) {
    for (i = 0; i < mT; i++) {
        rw = tbl.rows[i];
        if (rw.cells[c] == cll) return i;
    }
}