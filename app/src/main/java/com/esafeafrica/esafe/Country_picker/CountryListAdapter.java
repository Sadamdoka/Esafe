package com.esafeafrica.esafe.Country_picker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esafeafrica.esafe.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by GODARD Tuatini on 07/05/15.
 */
public class CountryListAdapter extends BaseAdapter {

    private static final String TAG = CountryListAdapter.class.getSimpleName();
    private final Context mContext;
    private final LayoutInflater inflater;
    private final List<Country> countries;
    private final boolean showDialingCode;

    public CountryListAdapter(Context context, List<Country> countries, boolean showDialingCode) {
        mContext = context;
        this.countries = countries;
        this.showDialingCode = showDialingCode;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        Item item;
        Country country = countries.get(position);

        if (convertView == null) {
            item = new Item();
            itemView = inflater.inflate(R.layout.item_country, parent, false);
            item.setIcon((ImageView) itemView.findViewById(R.id.icon));
            item.setName((TextView) itemView.findViewById(R.id.name));
            itemView.setTag(item);
        } else {
            item = (Item) itemView.getTag();
        }

        item.getName().setText(new Locale(mContext.getResources().getConfiguration().locale.getLanguage(),
                country.getIsoCode()).getDisplayCountry() + (showDialingCode ?
                " (+" + country.getDialingCode() + ")" : ""));

        // Load drawable dynamically from country code
        String drawableName = country.getIsoCode().toLowerCase(Locale.ENGLISH) + "_flag";
        item.getIcon().setImageResource(Utils.getMipmapResId(mContext, drawableName));
        return itemView;
    }

    public static class Item {
        private TextView name;
        private ImageView icon;

        public ImageView getIcon() {
            return icon;
        }

        public void setIcon(ImageView icon) {
            this.icon = icon;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }
    }
}
