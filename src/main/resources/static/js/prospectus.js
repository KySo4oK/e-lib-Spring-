window.onload = function () {
    let app = new Vue({
        el: '#app',
        data: {
            books: [],
            tags: [],
            addedTags: [],
            addedAuthors: [],
            partOfName: '',
            authors: [],
            page: 0,
            filter: false,
        },
        async mounted() {
            await this.getBooks();
            await this.getAuthors();
            await this.getTags()
        },
        methods: {
            async changeList() {
                this.page = 0;
                this.filter = true;
                await this.loadByFilter();
            },
            async loadByFilter() {
                let filter = {
                    tags: this.addedTags,
                    authors: this.addedAuthors,
                    name: '%' + this.partOfName + '%',
                };
                let res = await axios.post('/filter/' + this.page + "/5", filter)
                    .catch((error) => {
                        alert(error.response.data.error);
                    });
                if (!res) return;
                if (this.page === 0) {
                    this.books = res.data;
                } else {
                    this.books = this.books.concat(res.data);
                }
                this.page++;
            },
            async getTags() {
                let res = await axios.get('/tags');
                if (!res) return;
                this.tags = res.data;
            },
            async getBooks() {
                let res = await axios.get('/books/' + this.page + "/5");
                if (!res) return;
                this.books = this.books.concat(res.data);
            },
            async getAuthors() {
                let res = await axios.get('/authors');
                if (!res) return;
                this.authors = res.data;
            },
            async orderBook(book) {
                let res = await axios.post('/order', book)
                    .then((response) => {
                        alert(response.data.message);
                    })
                    .catch((error) => {
                        alert(error.response.data.error);
                    });
            },
            async loadMore() {
                if (this.filter) {
                    this.page++;
                    await this.loadByFilter();
                } else {
                    this.page++;
                    await this.getBooks();
                }
            }
        }
    });
}