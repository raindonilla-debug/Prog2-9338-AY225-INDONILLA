// Update your HTML Table Header first:
// <tr><th>Name</th><th>Course</th><th>Time In</th><th>Signature</th></tr>

function addAttendance() {
    const name = document.getElementById('name').value;
    const course = document.getElementById('course').value;
    const time = document.getElementById('clock').value;
    const canvas = document.getElementById('sig-canvas');

    if (name.trim() === "") {
        alert("Error: Name is required.");
        return;
    }

    // Convert Canvas drawing to an Image String (DataURL)
    const signatureData = canvas.toDataURL("image/png");

    const table = document.getElementById('historyTable').getElementsByTagName('tbody')[0];
    const row = table.insertRow(0);
    
    row.insertCell(0).innerText = name;
    row.insertCell(1).innerText = course;
    row.insertCell(2).innerText = time;
    
    // Create an image element for the signature cell
    const sigCell = row.insertCell(3);
    const sigImg = document.createElement("img");
    sigImg.src = signatureData;
    sigImg.style.width = "100px";
    sigImg.style.border = "1px solid #eee";
    sigCell.appendChild(sigImg);

    // Reset fields
    document.getElementById('name').value = "";
    clearCanvas();
}