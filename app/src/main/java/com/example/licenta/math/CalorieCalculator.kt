package com.example.licenta.math

import com.example.licenta.model.user.Gender

object CalorieCalculator {

    fun calculateForMaintaining(gender:Gender,height:Int,weight:Int,age:Int,activity: Double):Int{
        val genericBMR = 10.00*weight.toDouble() + 6.25*height.toDouble() - 5.00*age.toDouble()
        return if(gender == Gender.MALE){
            ((genericBMR + 5.00) * activity).toInt()
        }else{
            ((genericBMR - 161.00) * activity).toInt()
        }
    }

    fun calculateForFatLoss(gender: Gender,height: Int,weight: Int,age:Int,activity: Double):Int{
       val caloriesForMaintaining = calculateForMaintaining(gender,height,weight,age,activity)
        return when{
            caloriesForMaintaining < 2200 -> caloriesForMaintaining - (caloriesForMaintaining.toDouble() * 0.15).toInt()
            caloriesForMaintaining in 2000..3500 -> caloriesForMaintaining - (caloriesForMaintaining.toDouble()*0.2).toInt()
            else ->  caloriesForMaintaining - (caloriesForMaintaining.toDouble()*0.25).toInt()
        }
    }

    fun calculateForMuscleGain(gender: Gender,height: Int,weight: Int,age:Int,activity: Double):Int{
        val caloriesForMaintaining = calculateForMaintaining(gender,height,weight,age,activity)
        return when{
            caloriesForMaintaining < 2200 -> caloriesForMaintaining + (caloriesForMaintaining.toDouble() * 0.25).toInt()
            caloriesForMaintaining in 2000..3500 -> caloriesForMaintaining + (caloriesForMaintaining.toDouble()*0.2).toInt()
            else ->  caloriesForMaintaining + (caloriesForMaintaining.toDouble()*0.15).toInt()
        }
    }
}