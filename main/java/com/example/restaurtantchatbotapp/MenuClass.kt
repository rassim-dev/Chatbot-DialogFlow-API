package com.example.restaurtantchatbotapp



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurtantchatbotapp.adapters.MenuAdapter
import com.example.restaurtantchatbotapp.models.MenuList
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuClass : AppCompatActivity() {
    val foodType = arrayOf("Starter","Starter","Starter","Main Dish","Main Dish","Main Dish","Dessert","Dessert","Dessert")
    val foodName = arrayOf("Mushroom stuffed cheese","Cheese sticks" , "Cheese nachos","Italian margarita pizza","Pepperoni pizza" , "Chicken pizza","Chocolate pudding","Chocolate waffle" , "Ice cream vanilla & strawberry")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_menu)
        val menulist = makeList(9)
        recycler_view.adapter = MenuAdapter(menulist)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }
//here we make var called menulist using the methodMake list of size 9 using the List of type <MenuList> class
//recycler view eidget shows the information we have, makes sure it’s the correct layout linear, and has Boolean of fixed size for insurance


    private fun makeList(size: Int): List<MenuList> {
        val list = ArrayList<MenuList>()
        for (i in 0 until size) {
            val drawable = when (i % 9) {
                0 -> R.drawable.starter1
                1 -> R.drawable.starter2
                2 -> R.drawable.starter3
                3 -> R.drawable.pizza1
                4 -> R.drawable.pizza2
                5 -> R.drawable.pizza3
                6 -> R.drawable.dessert1
                7 -> R.drawable.dessert2
                else -> R.drawable.dessert3
            }
            val item = MenuList(drawable, foodType[i],foodName[i])
            list += item
        }
        return list
    }
}