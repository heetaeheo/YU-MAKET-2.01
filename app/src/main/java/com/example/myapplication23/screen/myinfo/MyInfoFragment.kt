package com.example.myapplication23.screen.myinfo

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication23.R
import com.example.myapplication23.databinding.FragmentMyInfoBinding
import com.example.myapplication23.screen.MainActivity
import com.example.myapplication23.screen.base.BaseFragment
import com.example.myapplication23.screen.myinfo.customerservice.configuration.ConfigurationFragment
import com.example.myapplication23.screen.myinfo.customerservice.personal.PersonalFragment
import java.lang.Exception


/**
 * @author HeeTae Heo(main),
 * Geonwoo Kim, Doyeop Kim, Namjin Jeong, Eunho Bae (sub)
 * @since
 * @throws
 * @description
 */
class MyInfoFragment  : BaseFragment< FragmentMyInfoBinding>()  {


    private lateinit var getResultImage : ActivityResultLauncher<Intent>


    private fun popUp() {
        requireContext().let { it1 -> PopUpMethod().popUp(it1) }
    }


    private val check = true;

    override fun getViewBinding() :FragmentMyInfoBinding =
        FragmentMyInfoBinding.inflate(layoutInflater)

    override fun observeData()  {

        /**
         * 추후 로그인 acesss->로 기능작동 하도록 initView()뒤로 이동예정
         */
    }
    companion object {
        const val TAG = "MyInfoFragment"

        fun newInstance() : MyInfoFragment {
            return MyInfoFragment().apply{

            }
        }
    }

    override fun initViews() = with(view)  {
        super.initViews()

        binding.terms.setOnClickListener { openTerms() }

        binding.profileImageChange.setOnClickListener { loadImage()}

        binding.darkSwitch.setOnClickListener { darkMode() }


        binding.noticeText.setOnClickListener { popUp() }

      binding.centerTextview.setOnClickListener { openCSCenter()}

        binding.setting.setOnClickListener { openSetting() }

        binding.personalTextview.setOnClickListener { openPersonal() }

        binding.back.setOnClickListener { back() }




        getResultImage = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            result -> if(result.resultCode == RESULT_OK){
            val dataUri : Uri? = result.data?.data
            try {
                val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,dataUri)
                binding.profileImage.setImageBitmap(bitmap)
            }  catch (e: Exception) {
                Toast.makeText(context,"$e",Toast.LENGTH_SHORT).show()
            }
        }
        }

    }

    private fun loadImage(){

        var intent_image = Intent()
        intent_image.type = "image/*"
        intent_image.action = Intent.ACTION_GET_CONTENT

        getResultImage.launch(intent_image)
//        startActivityForResult(Intent.createChooser(intent_image,"Load Picture"),galley)
    }

    private fun openSetting(){
        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_myInfoFragment_to_configurationFragment) }
    }

    private fun openTerms(){
        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_myInfoFragment_to_termsFragment) }
    }

    private fun openPersonal(){
        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_myInfoFragment_to_personalFragment) }
    }

    private fun back(){
        activity?.let{
            var intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun darkMode(){
        if(check  == binding.darkSwitch.isChecked){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    private fun openCSCenter(){
        view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_myInfoFragment_to_CSCenterFragment) }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(galley == requestCode){
//            if(resultCode == RESULT_OK){
//                val dataUri : Uri? = data?.data
//                try {
//                    val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,dataUri)
//                    binding.profileImage.setImageBitmap(bitmap)
//                }  catch (e: Exception) {
//                    Toast.makeText(context,"$e",Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }



}