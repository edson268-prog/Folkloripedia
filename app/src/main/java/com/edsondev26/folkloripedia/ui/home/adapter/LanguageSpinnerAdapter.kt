package com.edsondev26.folkloripedia.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.edsondev26.folkloripedia.R

class LanguageSpinnerAdapter(
    private val context: Context,
    private val languages: Array<String>,
    private val flags: IntArray
) : BaseAdapter() {
    override fun getCount(): Int {
        return languages.size
    }

    override fun getItem(position: Int): Any {
        return languages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // When the spinner is "closed"
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.spinner_item_icon, parent, false)
            holder = ViewHolder()
            holder.languageTextView = view.findViewById(R.id.tvLanguage)
            holder.languageImageView = view.findViewById(R.id.ivLanguageIcon)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

//        holder.languageTextView?.text = languages[position]
        holder.languageTextView?.visibility = View.GONE // Hide name of the language
        holder.languageImageView?.setImageResource(flags[position])

        return view
    }

    // When the spinner is "open"
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.spinner_item_icon, parent, false)
            holder = ViewHolder()
            holder.languageTextView = view.findViewById(R.id.tvLanguage)
            holder.languageImageView = view.findViewById(R.id.ivLanguageIcon)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        holder.languageTextView?.visibility = View.VISIBLE // Show language's name
        holder.languageTextView?.text = languages[position]
        holder.languageImageView?.setImageResource(flags[position])

        return view
    }

    private class ViewHolder {
        var languageTextView: TextView? = null
        var languageImageView: ImageView? = null
    }
}