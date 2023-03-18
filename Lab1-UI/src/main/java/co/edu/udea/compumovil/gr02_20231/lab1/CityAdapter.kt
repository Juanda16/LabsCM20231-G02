package co.edu.udea.compumovil.gr02_20231.lab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CityAdapter(private val cities: CitiesResponse) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities.data[position]
        holder.nameTextView.text = city
//        holder.countryTextView.text = city.countryCode
//        holder.latitudeTextView.text = "Latitude: ${city.latitude}"
//        holder.longitudeTextView.text = "Longitude: ${city.longitude}"
    }

    override fun getItemCount(): Int {
        return cities.data.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
//        val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)
//        val latitudeTextView: TextView = itemView.findViewById(R.id.latitudeTextView)
//        val longitudeTextView: TextView = itemView.findViewById(R.id.longitudeTextView)
    }
}
