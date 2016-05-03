package com.sibela.examples.orientationchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.sibela.examples.orientationchange.adapter.TitleDescriptionAdapter;
import com.sibela.examples.orientationchange.model.Book;
import com.sibela.examples.orientationchange.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MainActivity extends AppCompatActivity {

    private TitleDescriptionAdapter<Book> mBookAdapter;

    public static final String BOOKS = "books";
    public static final String TITLE = "title";
    public static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus urna ex, sagittis quis luctus a, iaculis a orci. Quisque in mi ipsum. Pellentesque a pellentesque nibh. Vestibulum dictum elementum dignissim.";

    @Bind(R.id.book_recycler_view)
    RecyclerView mBookRecycler;

    @Bind(R.id.title_input)
    EditText mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mBookRecycler.setLayoutManager(new LinearLayoutManager(this));
        mBookAdapter = new TitleDescriptionAdapter<>(getSomeBooks(2));
        mBookRecycler.setAdapter(mBookAdapter);
    }

    @OnClick(R.id.add_button)
    protected void addBookToList() {
        String bookTitle = mTitle.getText().toString();
        if (!bookTitle.isEmpty()) {
            Book book = new Book();
            book.setTitle(bookTitle);
            book.setDescription(LOREM_IPSUM);
            mBookAdapter.setContent(book);
            mTitle.setText("");
        }
    }

    @OnEditorAction(R.id.title_input)
    public boolean titleEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            addBookToList();
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String title = mTitle.getText().toString();
        String strBooks = GsonUtil.toJsonAsString(mBookAdapter.getItens());

        outState.putString(TITLE, title);
        outState.putString(BOOKS, strBooks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        List<Book> books = GsonUtil.fromJsonList(savedInstanceState.getString(BOOKS), Book.class);
        mBookAdapter.setContent(books);
        mTitle.setText(savedInstanceState.getString(TITLE));
    }

    private List<Book> getSomeBooks(int amount) {

        List<Book> books = new ArrayList<>();

        for (int i = 1; i <= amount; i++) {
            Book book = new Book();

            book.setTitle(getString(R.string.label_title) + " " + i);
            book.setDescription(LOREM_IPSUM);
            books.add(book);
        }

        return books;
    }
}
