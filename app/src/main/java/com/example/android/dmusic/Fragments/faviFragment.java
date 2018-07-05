package com.example.android.dmusic.Fragments;

import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.content.Loader;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.dmusic.MainActivity;
import com.example.android.dmusic.R;
import com.example.android.dmusic.adapters.customCusrorAdap;
import com.example.android.dmusic.data.contractClass;




public class faviFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private final static String API_KEY = "b4267de29314b95aa3c5e20b9a354a80";
    ListView searchList;
    customCusrorAdap adapter1;
    ProgressBar loader ;



    public faviFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu1,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {               //SET ONCLICK LISTER FOR MENU
        int id = item.getItemId();
        switch (id){
            case R.id.search1:
                getActivity().onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_result,container,false);
        searchList = (ListView)root.findViewById(R.id.searchResultList) ;
        loader = (ProgressBar)root.findViewById(R.id.progress2);
        loader.setVisibility(View.VISIBLE);
        adapter1 = new customCusrorAdap(getContext(),null);
        searchList.setAdapter(adapter1);
        loader.setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(0,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),contractClass.faviTable.CONTENT_URI , null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter1.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter1.swapCursor(null);
    }
}
