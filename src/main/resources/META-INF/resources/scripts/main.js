var app = new Vue({
    el: '#app',
    data: {
        todos: []
    },
    mounted() {
        axios.get("todos")
            .then(response => {
                this.todos = response.data
            })
    }
});
