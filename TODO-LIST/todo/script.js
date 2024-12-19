let button = document.getElementById("add");
let todoList = document.getElementById("todoList");
let input = document.getElementById("input");

let todos = [];//stored in array, so can display one by one & easy to delete
//array used mainly for local storage
window.onload = () =>{
    todos = JSON.parse(localStorage.getItem("todos")) || [];//to retrive the local storage data, stored as string so need to parse and get it
    //retrive the local storage data (or) leave it as an empty array
    //console.log(todos)
    todos.forEach(todo => addtodo(todo))
}
button.addEventListener("click",()=>{
    todos.push(input.value);
    //console.log(todos);
        //local storage
    //only added to local storage , still need to check everytime when pageloads, thus local stor has todos
    localStorage.setItem("todos",JSON.stringify(todos));//as need to store entire array it is converted to string using JSON
    addtodo(input.value);
    input.value="";//to empty the input field so can add next task
});

function addtodo(todo)
{
    let para = document.createElement("p");
    para.innerText = todo;
    todoList.appendChild(para);
    para.addEventListener("click",()=>{
        para.style.textDecoration = "line-through";
        remove(todo);//to remove from the array
    });
    para.addEventListener("dblclick",()=>{
        todoList.removeChild(para);//if double click remove from display
        remove(todo);//to remove from the display itself
    });
}
function remove(todo)
{
    let index = todos.indexOf(todo);
    if(index > -1)
        todos.splice(index,1);
    //localStorage.clear();
    localStorage.setItem('todos',JSON.stringify(todos));
}