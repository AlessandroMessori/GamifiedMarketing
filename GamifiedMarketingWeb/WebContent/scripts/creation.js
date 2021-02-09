let counter = 0;

const addQuestionBox = () => {
    const parent = document.getElementById('questionSection')
    const newBox = document.createElement("textarea")
    counter++;
    newBox.id = "question-" + counter;
    newBox.name = "question-" + counter;
    parent.appendChild(newBox)
}