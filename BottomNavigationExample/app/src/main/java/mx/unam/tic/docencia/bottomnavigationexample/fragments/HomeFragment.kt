package mx.unam.tic.docencia.pagerexample.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.unam.tic.docencia.bottomnavigationexample.R
import mx.unam.tic.docencia.pagerexample.listener.OnChangeColorBarTabSelected

private const val ARG_TITLE = "title"
private const val ARG_COLOR = "color"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class HomeFragment : Fragment() {

    private lateinit var title:String
    private lateinit var color:String
    private lateinit var onChangeColorBarTabSelected: OnChangeColorBarTabSelected

   /* override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnChangeColorBarTabSelected){
            onChangeColorBarTabSelected=context
        }
    }*/

    /*override fun onStart() {
        super.onStart()
        if(onChangeColorBarTabSelected!=null)
            onChangeColorBarTabSelected.OnChangeColorBar(title,color)
    }*/

    /*override fun onResume() {
        super.onResume()
        if(onChangeColorBarTabSelected!=null && isVisible)
            onChangeColorBarTabSelected.OnChangeColorBar(title,color)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            title=it!!.getString("title","PageExample")
            color=it!!.getString("color","FFFFFF")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = HomeFragment()

        @JvmStatic
        fun newInstance(title: String, color:String)=
            HomeFragment().apply{
                arguments=Bundle().apply{
                    this?.putString(ARG_TITLE, title)
                    this?.putString(ARG_COLOR, color)
                }
        }
    }
}
