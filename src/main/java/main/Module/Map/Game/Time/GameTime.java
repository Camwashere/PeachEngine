package main.Module.Map.Game.Time;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDateTime;

public class GameTime
{
    private final SimpleIntegerProperty second = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty minute = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty hour = new SimpleIntegerProperty(0);

    private final SimpleIntegerProperty day = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty month = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty year = new SimpleIntegerProperty(0);

    public GameTime()
    {
        AddListeners();
        LocalDateTime now = LocalDateTime.now();
        second.set(now.getSecond());
        minute.set(now.getMinute());
        hour.set(now.getHour());
        day.set(now.getDayOfWeek().getValue());
        month.set(now.getMonthValue());
        year.set(now.getYear());
    }
    public GameTime(int m, int d, int y)
    {
        AddListeners();
        day.set(d);
        month.set(m);
        year.set(y);
    }
    public GameTime(int sec, int min, int h, int d, int m, int y)
    {
        AddListeners();
        Set(sec, min, h, d, m, y);
    }


    private void AddListeners()
    {
        second.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.intValue()>59)
                {
                    Reset(second);
                    IncrementMinute();
                }
            }
        });
        minute.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.intValue() > 59)
                {
                    Reset(minute);
                    IncrementHour();
                }
            }
        });
        hour.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.intValue() > 23)
                {
                    IncrementDay();
                    ResetAll();
                }
            }
        });
        day.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.intValue() > GetMonthLength())
                {
                    Reset(day);
                    IncrementMonth();
                }
            }
        });

        month.addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1)
            {
                if (t1.intValue() > 12)
                {
                    Reset(month);
                    IncrementYear();
                }
            }
        });
    }
    public static String SearchDayOfWeek(int d, int m, int y)
    {
        int c = y / 100;
        String yVal = String.valueOf(y);
        y = Integer.parseInt(yVal.substring(yVal.length()-2));
        m -= 2;
        if (m <= 0)
        {
            m += 12;
        }
        if (m == 11 || m == 12)
        {
            y--;
        }
        int w = (d + (int)Math.floor(2.6*m - 0.2) - (2*c) + y + (int)Math.floor(y/4.0) + (int)Math.floor(c/4.0)) % 7;

        return switch (w)
                {
                    case 0 -> "Sunday";
                    case 1 -> "Monday";
                    case 2 -> "Tuesday";
                    case 3 -> "Wednesday";
                    case 4 -> "Thursday";
                    case 5 -> "Friday";
                    case 6 -> "Saturday";
                    default -> "Error";
                };

    }
    private void Reset(final SimpleIntegerProperty prop){prop.set(0);}
    private void ResetAll()
    {
        Reset(second);
        Reset(minute);
        Reset(hour);
    }
    public final boolean IsLeapYear(){return (((GetYear() % 4 == 0) && (GetYear() % 100 != 0)) || (GetYear() % 400 == 0));}
    public final String GetSeason()
    {
        if (GetMonth() >= 3 && GetMonth() < 6)
        {
            return "Spring";
        }
        else if (GetMonth() >= 6 && GetMonth() < 9)
        {
            return "Summer";
        }
        else if (GetMonth() >= 9 && GetMonth() < 12)
        {
            return "Fall";
        }
        else
        {
            return "Winter";
        }
    }

    public final void SetSecond(int val){second.set(val);}
    public final void SetMinute(int val){minute.set(val);}
    public final void SetHour(int val){hour.set(val);}
    public final void SetDay(int val){day.set(val);}
    public final void SetMonth(int val){month.set(val);}
    public final void SetYear(int val){year.set(val);}
    public final void SetDate(int m, int d, int y)
    {
        SetMonth(m);
        SetDay(d);
        SetYear(y);
    }
    public final void SetTime(int sec, int min, int h)
    {
        SetSecond(sec);
        SetMinute(min);
        SetHour(h);
    }
    public final void Set(int sec, int min, int h, int d, int m, int y)
    {
        second.set(sec);
        minute.set(min);
        hour.set(h);
        day.set(d);
        month.set(m);
        year.set(y);
    }

    public final void IncrementSecond(){second.set(GetSecond()+1);}
    public final void IncrementMinute(){minute.set(GetMinute()+1);}
    public final void IncrementHour(){hour.set(GetHour()+1);}
    public final void IncrementDay(){day.set(GetDay()+1);}
    public final void IncrementMonth(){month.set(GetMonth()+1);}
    public final void IncrementYear(){year.set(GetYear()+1);}
    public final void IncrementSeconds(int amount){second.set(GetSecond() + amount);}
    public final void IncrementMinutes(int amount){minute.set(GetMinute() + amount);}
    public final void IncrementHours(int amount){hour.set(GetHour() + amount);}
    public final void IncrementDays(int amount){day.set(GetDay()+amount);}
    public final void IncrementMonths(int amount){month.set(GetMonth()+amount);}
    public final void IncrementYears(int amount){year.set(GetYear()+amount);}


    public final int GetDay(){return day.get();}
    public final int GetMonth(){return month.get();}
    public final int GetYear(){return year.get();}
    public final int GetCentury(){return GetYear() / 100;}
    public final int GetSecond(){return second.get();}
    public final int GetMinute(){return minute.get();}
    public final int GetHour(){return hour.get();}
    public final String GetDayName() {return SearchDayOfWeek(GetDay(), GetMonth(), GetYear());}
    public final String GetMonthName()
    {
        return switch (month.get())
                {
                    case 1 ->
                            // January
                            "January";
                    case 2 ->
                            // Feb
                            "February";
                    case 3 ->
                            // March
                            "March";
                    case 4 ->
                            // April
                            "April";
                    case 5 ->
                            //May
                            "May";
                    case 6 ->
                            //June
                            "June";
                    case 7 ->
                            // July
                            "July";
                    case 8 ->
                            //August
                            "August";
                    case 9 ->
                            //Sep
                            "September";
                    case 10 ->
                            //October
                            "October";
                    case 11 ->
                            //Nov
                            "November";
                    case 12 ->
                            //Dec
                            "December";
                    default -> "ERROR";
                };
    }
    public final int GetMonthLength()
    {
        switch(month.get())
        {
            case 1:
                // January
                return 31;
            case 2:
                // Feb
                if (IsLeapYear())
                {
                    return 29;
                }
                return 28;
            case 3:
                // March
                return 31;
            case 4:
                // April
                return 30;
            case 5:
                //May
                return 31;
            case 6:
                //June
                return 30;
            case 7:
                // July
                return 31;
            case 8:
                //August
                return 31;
            case 9:
                //Sep
                return 30;
            case 10:
                //October
                return 31;
            case 11:
                //Nov
                return 30;
            case 12:
                //Dec
                return 31;
            default:
                return 30;

        }
    }
    public final String GetPeriod()
    {
        int hour = GetHour();
        if (hour >= 4 && hour < 6)
        {
            return "Early Morning";
        }
        else if (hour >= 6 && hour < 9)
        {
            return "Morning";
        }
        else if (hour >= 9 && hour < 12)
        {
            return "Late Morning";
        }
        else if (hour >= 12 && hour < 17)
        {
            return "Afternoon";
        }
        else if (hour >= 17 && hour < 21)
        {
            return "Evening";
        }
        else if (hour >=21 && hour < 23)
        {
            return "Late Evening";
        }
        else if (hour == 23 || hour < 4)
        {
            return "Night";
        }
        else
        {
            return "Mistake";
        }
    }



    public final void AdvanceTimeBy(final GameTime other)
    {
        IncrementSeconds(other.GetSecond());
        IncrementMinutes(other.GetMinute());
        IncrementHours(other.GetHour());
        IncrementDays(other.GetDay());
        IncrementMonths(other.GetMonth());
        IncrementYears(other.GetYear());
    }
    public final GameTime TimeBetween(final GameTime other)
    {
        int sec = Math.abs(GetSecond() - other.GetSecond());
        int min = Math.abs(GetMinute() - other.GetMinute());
        int hour = Math.abs(GetHour() - other.GetHour());
        int day = Math.abs(GetDay() - other.GetDay());
        int month = Math.abs(GetMonth() - other.GetMonth());
        int year = Math.abs(GetYear() - other.GetYear());
        return new GameTime(sec, min, hour, day, month, year);
    }
    public final int YearsBetween(final GameTime other)
    {
        return TimeBetween(other).GetYear();
    }


    public final String TimeString()
    {
        String str = "";
        boolean AM = false;
        if (GetHour() < 1)
        {
            str = String.valueOf(GetHour() + 12);
            AM = true;
        }

        else if (GetHour() >= 1 && GetHour() < 12)
        {
            if (GetHour() < 10)
            {
                str = "0" + String.valueOf(GetHour());
            }
            else
            {
                str = String.valueOf(GetHour());
            }
            AM = true;
        }
        else if (GetHour() == 12)
        {
            str = String.valueOf(GetHour());
        }

        else if (GetHour() >= 13)
        {
            if (GetHour() - 12 < 10)
            {
                str = "0" + String.valueOf(GetHour() - 12);
            }
            else
            {
                str = String.valueOf(GetHour() - 12);
            }
        }

        String minutesString = "";
        if (GetMinute() < 10)
        {
            minutesString = "0" + GetMinute();
        }
        else
        {
            minutesString = String.valueOf(GetMinute());
        }

        str += ":" + minutesString;
        if (AM)
        {
            str += "AM";
        }
        else
        {
            str += "PM";
        }
        return str;
    }
    public final String DateString()
    {
        String monthStr = String.valueOf(GetMonth());
        String dayStr = String.valueOf(GetDay());
        String yearStr = String.valueOf(GetYear());
        return monthStr + "/" + dayStr + "/" + yearStr;
    }
    @Override
    public String toString() {return "Date: " + DateString() + " Time: " + TimeString();}


    public static int YearsToDays(int numYears){return numYears * 365;}
    public static int DaysToHours(int numDays){return numDays * 24;}
    public static int HoursToMinutes(int numHours){return numHours * 60;}
    public static int MinutesToSeconds(int numMinutes){return numMinutes * 60;}
}
