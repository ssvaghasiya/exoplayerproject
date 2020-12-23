package com.newproject.ui.home.datamodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.newproject.datasource.AttendanceRepository
import com.newproject.network.APIClient
import com.newproject.network.APIinterface

class AttendanceDataModel {

    fun getEmployeeAttendance(context: Context): MutableLiveData<AttendanceData> {
        val apInterface: APIinterface =
            APIClient.newRequestRetrofit(context).create(APIinterface::class.java)
        val attendanceRepository = AttendanceRepository(apInterface)
        return attendanceRepository.getEmployeeAttendance()
    }
}