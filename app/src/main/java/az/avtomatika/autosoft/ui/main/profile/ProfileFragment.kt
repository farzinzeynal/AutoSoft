package az.avtomatika.autosoft.ui.main.profile

import android.os.Bundle
import android.view.View
import az.avtomatika.autosoft.base.BaseFragment
import az.avtomatika.autosoft.databinding.FragmentProfileBinding
import az.avtomatika.autosoft.util.Constants

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        views.inputName.editText?.setText(Constants.profileDatas?.firstname)
        views.inputSureName.editText?.setText(Constants.profileDatas?.lastname)
        views.inputAdress.editText?.setText(Constants.profileDatas?.address)
        views.inputPhone.editText?.setText(Constants.profileDatas?.telephone)
        views.inputBirthday.editText?.setText(Constants.profileDatas?.birthday)
        views.userPhoto.setImageBitmap(Constants.registeredUserImage)


    }
}