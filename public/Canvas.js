const tileSize = 10;
const timerDurationUser = 5;
const timerDurationGuest = 10;

let colorSelected;
const canvas = document.getElementById("myCanvas");
const context = canvas.getContext("2d");
canvas.addEventListener("click", function (event) {
    draw(event);
});

const maskCanvas = document.createElement("canvas");
maskCanvas.width = 1000;
maskCanvas.height = 500;
const maskContext = maskCanvas.getContext("2d");

function pick(x, y) {
    const pixel = maskContext.getImageData(x, y, 1, 1);
    const data = pixel.data;
    return data[0] === 255;
}

function getCustomerId() {
    const customerId = sessionStorage.customerId;
    if (customerId === undefined){
        return "guest@canvas.usc";
    }else{
        return customerId;
    }
}

//hehe:D

function draw(e) {
    console.log("Received clicking...");
    const x = getMousePos(canvas, e).x;
    const y = getMousePos(canvas, e).y;

    if (!pick(x, y)) {
        return;
    }

    if (countingDown()) {
        if (getCustomerId() === null)
            alert("Please wait for your timer counting down! If you want to decrese waiting time to 5 seconds, register!")
        else
            alert("Please wait for your timer counting down!");
        return;
    }

    console.log("Begin drawing...");
    const x1 = x - x % tileSize;
    const y1 = y - y % tileSize;
    context.fillStyle = colorSelected;
    context.fillRect(x1, y1, tileSize, tileSize);

    if (getCustomerId() === null)
        startTimer(timerDurationGuest);
    else
        startTimer(timerDurationUser);

    const costumerId = getCustomerId();
    drawColor(x1, y1, colorSelected, costumerId);
}

const url = "https://pixel-canvas-usc.ngrok.io/";

async function getColor() {
    let requestUrl = url + "panel/getColors";
    try {
        let response = await fetch(requestUrl);
        return await response.json();
    } catch (error) {
        console.log('request failed', error);
    }
}

async function drawColor(x, y, color, email) {
    let requestUrl = url + "panel/drawColor?x_coordinate=" + x
        + "&y_coordinate=" + y + "&color=" + color + "&email=" + email;
    try {
        let response = await fetch(requestUrl);
        console.log(await response.text());
    } catch (error) {
        console.log('request failed', error);
    }
}


const min_display = document.getElementById("mins_left");
const sec_display = document.getElementById("secs_left");
let diff;

const canvasImage = new Image();
canvasImage.src = "resource/canvas.png";
canvasImage.crossOrigin = "Anonymous";
canvasImage.onload = function () {
    context.drawImage(canvasImage, 0, 0);
};

function init() {
    console.log(sessionStorage.customerId);
    console.log("init..");

    //display username
    const username_display = document.getElementById("isLoggedin");
    username_display.innerText = getCustomerId();

    const buttonList = document.getElementsByClassName("paletteB");
    for (const b of buttonList) {
        b.style.backgroundColor = b.id;
        b.onclick = function () {
            changeColor(b.id)
        };
    }

    changeColor("red");

    const img = new Image();
    img.src = "resource/USC2.bmp";
    img.crossOrigin = "Anonymous";
    img.onload = function () {
        maskContext.drawImage(img, 0, 0);
    };

    updateCanvas();
    setInterval(updateCanvas, 1000);
}

function startTimer(duration) {
    const timerFunc = setInterval(timer, 1000);
    const start = Date.now();
    let minutes, seconds;

    function timer() {
        diff = duration - (((Date.now() - start) / 1000) | 0);

        minutes = (diff / 60) | 0;
        seconds = (diff % 60) | 0;

        if (minutes < 0) {
            minutes = 0;
        }
        if (seconds < 0) {
            seconds = 0;
        }

        min_display.textContent = minutes;
        sec_display.innerText = seconds;

        if (diff <= 0) {
            clearInterval(timerFunc);
        }
    }

    timer();
}

function countingDown() {
    return diff > 0;
}

function changeColor(color) {
    colorSelected = color;
    const buttonList = document.getElementsByClassName("paletteB");
    for (const b of buttonList) {
        b.style.border = "1px solid black";
    }
    document.getElementById(color).style.border = "3px solid black";
}

function getMousePos(c, e) {
    const rect = c.getBoundingClientRect();
    return {
        x: e.clientX - rect.left,
        y: e.clientY - rect.top
    };
}

async function drawCanvas() {
    var rectList = getColor();

    for (var rect of await rectList) {
        context.fillStyle = rect.color;
        context.fillRect(rect.x_coordinate, rect.y_coordinate, tileSize, tileSize);
    }
}

function updateCanvas() {
    console.log("updating canvas");
    drawCanvas();
}
