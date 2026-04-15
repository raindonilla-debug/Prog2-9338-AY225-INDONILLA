// --- Step 1: Initialize Audio and Data Structures ---
// Requirement: Create a new Audio object for the beeping sound
const beep = new Audio('beep.mp3'); 
let attendanceRecords = []; // Simple data structure to hold attendance records

// --- 1. Running Time Logic ---
function updateTime() {
    const now = new Date();
    // Requirement: Format date and time in a readable format
    const timeStr = now.getFullYear() + '-' + 
        String(now.getMonth() + 1).padStart(2, '0') + '-' + 
        String(now.getDate()).padStart(2, '0') + ' ' + 
        now.toTimeString().split(' ')[0];
    
    const timeDisplay = document.getElementById('timeIn');
    if (timeDisplay) timeDisplay.value = timeStr;
}
setInterval(updateTime, 1000);
updateTime();

// --- Step 2: Login System Implementation ---
// Requirement: Validate username and password against predefined values
function handleLogin() {
    const user = document.getElementById('loginUser').value;
    const pass = document.getElementById('loginPass').value;

    if (user === "admin" && pass === "1234") {
        alert("Login Successful! Welcome, " + user);
        // Requirement: Display the timestamp section after successful login
        document.getElementById('loginSection').style.display = 'none';
        document.getElementById('attendanceSection').style.display = 'block';
        document.getElementById('name').value = user;
    } else {
        // Requirement: Trigger beeping sound if login fails
        beep.play();
        alert("Incorrect password!");
    }
}

// --- 2. Drawing Signature Logic ---
const canvas = document.getElementById('sigCanvas');
const ctx = canvas.getContext('2d');
const deleteBtn = document.getElementById('deleteBtn');
const enterBtn = document.getElementById('enterBtn');
const historyBody = document.querySelector('#historyTable tbody');
let drawing = false;

canvas.width = canvas.offsetWidth;
canvas.height = canvas.offsetHeight;
ctx.strokeStyle = "#2d3436";
ctx.lineWidth = 2;
ctx.lineCap = "round";

function startDrawing(e) { drawing = true; draw(e); }
function stopDrawing() { drawing = false; ctx.beginPath(); }

function draw(e) {
    if (!drawing) return;
    const rect = canvas.getBoundingClientRect();
    const clientX = e.clientX || (e.touches ? e.touches[0].clientX : 0);
    const clientY = e.clientY || (e.touches ? e.touches[0].clientY : 0);
    ctx.lineTo(clientX - rect.left, clientY - rect.top);
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(clientX - rect.left, clientY - rect.top);
}

canvas.addEventListener('mousedown', startDrawing);
canvas.addEventListener('mousemove', draw);
window.addEventListener('mouseup', stopDrawing);
deleteBtn.addEventListener('click', () => ctx.clearRect(0, 0, canvas.width, canvas.height));

// --- 3. Entry Submission & History Table ---
enterBtn.addEventListener('click', () => {
    const nameInput = document.getElementById('name');
    const courseInput = document.getElementById('course');
    const timeVal = document.getElementById('timeIn').value;

    if(!nameInput.value.trim() || !courseInput.value.trim()) {
        beep.play(); // Play sound if validation fails
        alert("Please fill in all fields.");
        return;
    }

    // Save to data structure for summary
    attendanceRecords.push({ username: nameInput.value, timestamp: timeVal });

    const sigDataURL = canvas.toDataURL();
    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${nameInput.value}</td>
        <td>${courseInput.value}</td>
        <td>${timeVal}</td>
        <td><img src="${sigDataURL}" class="sig-preview" style="height:40px;"></td>
    `;
    historyBody.prepend(row);

    // Clear signature and course for next entry
    courseInput.value = '';
    ctx.clearRect(0, 0, canvas.width, canvas.height);
});

// --- Step 5: Generating Attendance Summary ---
// Requirement: Use Blob API to generate a text file summary
function downloadSummary() {
    let summaryContent = "Attendance Summary\n\n";
    attendanceRecords.forEach(record => {
        summaryContent += `Username: ${record.username} | Timestamp: ${record.timestamp}\n`;
    });

    const blob = new Blob([summaryContent], { type: 'text/plain' });
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = 'attendance_summary.txt';
    link.click(); // Trigger the download
}