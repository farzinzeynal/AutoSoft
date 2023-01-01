package az.avtomatika.autosoft.ui.main.home

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import az.avtomatika.autosoft.R
import az.avtomatika.autosoft.base.BaseFragment
import az.avtomatika.autosoft.databinding.FragmentHomeBinding
import az.avtomatika.autosoft.model.remote.response.LastShifData
import az.avtomatika.autosoft.ui.main.addform.FormViewModel
import az.avtomatika.autosoft.ui.splash.SplashVeiwModel
import az.avtomatika.autosoft.util.Constants
import az.avtomatika.autosoft.util.NetworkResult
import az.avtomatika.autosoft.util.PopupAnimTypes
import az.avtomatika.autosoft.util.UtilFunctions.getNavOptions
import az.avtomatika.autosoft.util.core.MainPopupDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), View.OnClickListener{

    private val viewModel: FormViewModel by viewModel()
    private var shiftType= ""

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initLastShiftApi()
        viewModel.getLastShiftType()



    }

    private fun initLastShiftApi() {
        viewModel.getLastShiftLiveData.observe(viewLifecycleOwner){
            when (it) {
                is NetworkResult.Success -> {
                    if (it.response?.data != null) {
                        setDatas(it.response.data)
                    } else {
                        MainPopupDialog.infoAlert(
                            requireContext(),
                            MainPopupDialog.InfoDatas("Diqqət", it.response?.error?.message ?: ""),
                            animType = PopupAnimTypes.ERROR
                        )
                    }
                }
                is NetworkResult.Error -> {
                    MainPopupDialog.infoAlert(
                        requireContext(),
                        MainPopupDialog.InfoDatas("Diqqət", it.message.toString()),
                        animType = PopupAnimTypes.ERROR
                    )
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun setDatas(data: LastShifData?) {
        views.lastShiftDate.text = "Sonuncu əməliyyatın tarixi: \n"+data?.date
        Constants.currentShifType = data?.type ?: ""
        if(data?.type=="1"){
            views.btnStartForm.text = "İşə başla"
        }
        else {
            views.btnStartForm.text = "İşi bitir"
        }
    }

    private fun initViews() {
        views.btnStartForm.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnStartForm -> {
                findNavController().navigate(R.id.addNewFromFragment, bundleOf("shiftType" to shiftType), getNavOptions())
            }
        }
    }

}