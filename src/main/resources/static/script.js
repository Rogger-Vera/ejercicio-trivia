let categories = [];
let question = {};
let totalDegree = 0;
document.addEventListener('DOMContentLoaded', function() {
    let dialog = document.getElementById('dialog');
    let result = document.getElementById('result');
    let btnSend = document.getElementById('btnSend');
    let btnClose = document.getElementById('btnClose');
    let errorMessage = document.getElementById('error-message');
    let spins = 0;

    dialog.style.display = 'none';
    result.style.display = 'none';

    load();

    document.getElementById('btnRefresh').addEventListener('click', function() {
        load();
    });

    document.querySelector('.spin-button').addEventListener('click', function() {
        spins++;
        let resultRandom = Math.floor(Math.random() * categories.length);
        let selectedCategory = document.querySelectorAll('.wheel .section span')[resultRandom].textContent;
        let categoryIndex = categories.findIndex(cat => cat.name === selectedCategory);
        loadQuestion(selectedCategory, categoryIndex);

        totalDegree = 61 * (resultRandom + 1);
        totalDegree += spins * 180 * 6;
        document.getElementById('wheel').style.transform = 'rotate(' + totalDegree + 'deg)';

        setTimeout(function() {
            btnClose.style.display = 'none';
            btnSend.style.display = 'block';
            document.getElementById('explanation').textContent = '';
            result.style.display = 'none';

            document.getElementById('dialog').querySelectorAll('input[type="radio"]').forEach(radio => {
                radio.checked = false;
            });
            document.getElementById('explanation').textContent = question.explanation;
            document.querySelector('.category').textContent = selectedCategory;
            document.querySelector('.question').textContent = question.question;
            document.querySelector('label[for="option1"]').textContent = question.options[0];
            document.querySelector('label[for="option2"]').textContent = question.options[1];
            document.querySelector('label[for="option3"]').textContent = question.options[2];

            dialog.style.display = 'block';
        }, 1700);
    });

    btnClose.addEventListener('click', function() {
        dialog.style.display = 'none';
    });

    btnSend.addEventListener('click', function() {

        let optionChecked = optionIsChecked();
        if(!optionChecked){
            errorMessage.style.display = 'block';
            return;
        }
        errorMessage.style.display = 'none';

        btnSend.style.display = 'none';
        btnClose.style.display = 'block';
        result.style.display = 'block';

        if (document.querySelector('#dialog #option' + (question.answer+1)).checked) {
            document.getElementById('result-ok').style.display = 'block';
            document.getElementById('result-fail').style.display = 'none';
        } else {
            document.getElementById('result-ok').style.display = 'none';
            document.getElementById('result-fail').style.display = 'block';
        }
    });

});

function optionIsChecked(){
    let radioButtons = document.querySelectorAll('#dialog input[type="radio"]');
    let optionChecked = false;
    for(radioButton of radioButtons){
        if(radioButton.checked){
            optionChecked = true;
            break;
        }
    }
    return optionChecked;
}

async function loadQuestion(category, categoryIndex) {
    let allowOrigin = 'https://api.allorigins.win/raw?url=';
    if (document.getElementById('txtUrlQuestion').value.includes('localhost')) {
        allowOrigin = '';
    }
    let urlQuestion = allowOrigin + document.getElementById('txtUrlQuestion').value + '/' + category;
    let response = await fetch(urlQuestion);
    question = await response.json();
}

async function load() {
    let allowOrigin = 'https://api.allorigins.win/raw?url=';
    if (document.getElementById('txtUrlQuestion').value.includes('localhost')
    || document.getElementById('txtUrlCategories').value.includes('localhost')) {
        allowOrigin = '';
    }
    let urlCategories = allowOrigin + document.getElementById('txtUrlCategories').value;
    response = await fetch(urlCategories);
    categories = await response.json();
    for (let i = 0; i < categories.length; i++) {
        document.querySelectorAll('.wheel .section span')[i].innerHTML = categories[i].name;
    }
}