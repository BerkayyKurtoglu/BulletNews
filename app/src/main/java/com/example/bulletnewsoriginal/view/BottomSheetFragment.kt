package com.example.bulletnewsoriginal.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.bulletnewsoriginal.R
import com.example.bulletnewsoriginal.model.CategoryDatabaseItem
import com.example.bulletnewsoriginal.util.SharedPreferenceService
import com.example.bulletnewsoriginal.viewModel.MainFragmentViewModel
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.SlideDistanceProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : Fragment() {

    private val categoryItem1 = CategoryDatabaseItem("")
    private val categoryItem2 = CategoryDatabaseItem("")
    private val categoryItem3 = CategoryDatabaseItem("")
    private val categoryItem4 = CategoryDatabaseItem("")
    private val categoryItem5 = CategoryDatabaseItem("")
    private val categoryItem6 = CategoryDatabaseItem("")
    private val categoryItem7 = CategoryDatabaseItem("")


    private lateinit var mainFragmentViewModel : MainFragmentViewModel
    private lateinit var sharedPreferenceService : SharedPreferenceService

    override fun onCreate(savedInstanceState: Bundle?) {
        enterTransition = MaterialFadeThrough().apply {
            secondaryAnimatorProvider = SlideDistanceProvider(Gravity.BOTTOM)
        }
        exitTransition =  MaterialFadeThrough().apply {
            secondaryAnimatorProvider = SlideDistanceProvider(Gravity.BOTTOM)
        }
        //sharedElementEnterTransition = MaterialContainerTransform()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleSystemUI()

        mainFragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        sharedPreferenceService = SharedPreferenceService(requireContext())
        handleCheckboxes()
        clickedApplyButton()

        super.onViewCreated(view, savedInstanceState)
    }


    private fun handleCheckboxes(){

        bottom_sheet_first_checkBox.isChecked = sharedPreferenceService.controlCheckState("first_checkBox")!!
        bottom_sheet_first_checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                sharedPreferenceService.editCheckState("first_checkBox",true)
                categoryItem1.id = 1
                categoryItem1.updateClass(buttonView.text.toString())
                mainFragmentViewModel.insertCategoryToRoom(categoryItem1)
            }else{
                sharedPreferenceService.editCheckState("first_checkBox",false)
                mainFragmentViewModel.deleteCategory(buttonView.text.toString())
            }
        }

        bottom_sheet_second_checkBox.isChecked = sharedPreferenceService.controlCheckState("second_checkBox")!!
        bottom_sheet_second_checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                sharedPreferenceService.editCheckState("second_checkBox",true)
                categoryItem2.id = 2
                categoryItem2.updateClass(buttonView.text.toString())
                mainFragmentViewModel.insertCategoryToRoom(categoryItem2)
            }else{
                sharedPreferenceService.editCheckState("second_checkBox",false)
                mainFragmentViewModel.deleteCategory(buttonView.text.toString())
            }
        }

        bottom_sheet_third_checkBox.isChecked = sharedPreferenceService.controlCheckState("third_checkBox")!!
        bottom_sheet_third_checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                sharedPreferenceService.editCheckState("third_checkBox",true)
                categoryItem3.id = 3
                categoryItem3.updateClass(buttonView.text.toString())
                mainFragmentViewModel.insertCategoryToRoom(categoryItem3)
            }else{
                sharedPreferenceService.editCheckState("third_checkBox",false)
                mainFragmentViewModel.deleteCategory(buttonView.text.toString())
            }
        }

        bottom_sheet_fourth_checkBox.isChecked = sharedPreferenceService.controlCheckState("fourth_checkBox")!!
        bottom_sheet_fourth_checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                sharedPreferenceService.editCheckState("fourth_checkBox",true)
                categoryItem4.id = 4
                categoryItem4.updateClass(buttonView.text.toString())
                mainFragmentViewModel.insertCategoryToRoom(categoryItem4)
            }else{
                sharedPreferenceService.editCheckState("fourth_checkBox",false)
                mainFragmentViewModel.deleteCategory(buttonView.text.toString())
            }
        }

        bottom_sheet_fifth_checkBox.isChecked = sharedPreferenceService.controlCheckState("fifth_checkBox")!!
        bottom_sheet_fifth_checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                sharedPreferenceService.editCheckState("fifth_checkBox",true)
                categoryItem5.id = 5
                categoryItem5.updateClass(buttonView.text.toString())
                mainFragmentViewModel.insertCategoryToRoom(categoryItem5)
            }else{
                sharedPreferenceService.editCheckState("fifth_checkBox",false)
                mainFragmentViewModel.deleteCategory(buttonView.text.toString())
            }
        }

        bottom_sheet_sixth_checkBox.isChecked = sharedPreferenceService.controlCheckState("sixth_checkBox")!!
        bottom_sheet_sixth_checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                sharedPreferenceService.editCheckState("sixth_checkBox",true)
                categoryItem6.id = 6
                categoryItem6.updateClass(buttonView.text.toString())
                mainFragmentViewModel.insertCategoryToRoom(categoryItem6)
            }else{
                sharedPreferenceService.editCheckState("sixth_checkBox",false)
                mainFragmentViewModel.deleteCategory(buttonView.text.toString())
            }
        }

        bottom_sheet_seventh_checkBox.isChecked = sharedPreferenceService.controlCheckState("seventh_checkBox")!!
        bottom_sheet_seventh_checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                sharedPreferenceService.editCheckState("seventh_checkBox",true)
                categoryItem7.id = 7
                categoryItem7.updateClass(buttonView.text.toString())
                mainFragmentViewModel.insertCategoryToRoom(categoryItem7)
            }else{
                sharedPreferenceService.editCheckState("seventh_checkBox",false)
                mainFragmentViewModel.deleteCategory(buttonView.text.toString())
            }
        }
    }

    private fun clickedApplyButton(){
        bottom_sheet_fragment_all_categories_button.setOnClickListener {
            findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetFragment2ToHomeFragment())
        }
    }

    private fun handleSystemUI(){
        (activity as AppCompatActivity).activity_appbar.apply {
            this.visibility = View.GONE
            this.elevation = 0F
        }
        (activity as AppCompatActivity).activity_toolbar.apply {
            this.title="Most Recommended"
            this.elevation = 0F
        }
        (activity as AppCompatActivity).activity_bottom_menu.visibility = View.GONE
        (activity as AppCompatActivity).activity_FAB.hide()

    }


}