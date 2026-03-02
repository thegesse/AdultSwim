const terminal = document.getElementById('terminal');
const input = document.getElementById('commandInput');

// Command history
let commandHistory = [];
let historyIndex = -1;

input.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        const command = this.value.trim();
        if (command) {
            commandHistory.push(command);
            historyIndex = commandHistory.length;
            executeCommand(command);
            this.value = '';
        }
    }
});

input.addEventListener('keydown', function(e) {
    if (e.key === 'ArrowUp') {
        e.preventDefault();
        if (historyIndex > 0) {
            historyIndex--;
            this.value = commandHistory[historyIndex];
        }
    } else if (e.key === 'ArrowDown') {
        e.preventDefault();
        if (historyIndex < commandHistory.length - 1) {
            historyIndex++;
            this.value = commandHistory[historyIndex];
        } else {
            historyIndex = commandHistory.length;
            this.value = '';
        }
    }
});

function executeCommand(command) {
    addOutput('> ' + command);

    fetch('/terminal/execute', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: 'input=' + encodeURIComponent(command)
    })
        .then(response => response.text())
        .then(result => {
            if (result.trim().startsWith('{')) {
                try {
                    const action = JSON.parse(result);
                    handleAction(action);
                } catch (e) {
                    addOutput(result);
                }
            } else {
                addOutput(result);
            }
            addOutput('---');
        })
        .catch(error => {
            addOutput('Error: ' + error);
            addOutput('---');
        });
}

function addOutput(text) {
    const line = document.createElement('div');
    line.className = 'output-line';
    line.textContent = text;
    terminal.appendChild(line);
    terminal.scrollTop = terminal.scrollHeight;
}

function handleAction(action) {
    switch(action.action) {
        case 'clear':
            terminal.innerHTML = '';
            break;
        case 'typewriter':
            typewriterEffect(action.text, action.pauseChar, action.delay);
            break;
        default:
            addOutput(JSON.stringify(action));
    }
}

function typewriterEffect(text, pauseChar, delay) {
    const line = document.createElement('div');
    line.className = 'output-line';
    terminal.appendChild(line);

    let i = 0;
    function type() {
        if (i < text.length) {
            const char = text.charAt(i);
            line.textContent += char;
            terminal.scrollTop = terminal.scrollHeight;

            const wait = (char === pauseChar) ? delay : 30;
            i++;
            setTimeout(type, wait);
        }
    }
    type();
}

function insertCommand(cmd) {
    input.value = cmd;
    input.focus();
}

// Focus input on click anywhere
document.addEventListener('click', () => input.focus());

// Initial focus
input.focus();