package com.accherniakocich.android.findjob.find;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.accherniakocich.android.findjob.classes.Ad;
import com.accherniakocich.android.findjob.activities.log_and_reg.MainActivity;
import com.accherniakocich.android.findjob.R;
import com.accherniakocich.android.findjob.classes.User;
import com.accherniakocich.android.findjob.findResult.FindResults;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

public class Find extends AppCompatActivity {

    private RangeSeekBar rangeSeekBar;

    private EditText edit_text_find;
    private Button premium, not_premium, button_find;
    private TextView minPrice, maxPrice;
    private CheckBox checkBox_only_with_photo;
    private Spinner spinner_category_find,spinner_city_find;
    private ArrayList<Ad>list;
    private ArrayList<Ad>list_result;
    private boolean premium_check = false;
    private User user;
    private String tP = "https://firebasestorage.googleapis.com/v0/b/findjob-51270.appspot.com/o/empty.png?alt=media&token=3d73aba3-5e79-4643-82dc-244c6ce326fb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        init();
        downloadListAds();
    }

    private void downloadListAds() {
        list = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("ads")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            list.add(postSnapshot.getValue(Ad.class));
                        }

                        Log.d(MainActivity.LOG_TAG,"list size = " + list.get(0).toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void findLogic() {
        list_result = new ArrayList<>();
        if (minPrice.getText().toString().equals("")){
            minPrice.setText("0");
        }
        if (maxPrice.getText().toString().equals("")){
            maxPrice.setText("100000");
        }
        if (!edit_text_find.getText().toString().equals("")){

            for (int i = 0;i<list.size();i++){

                if (list.get(i).toString().contains(edit_text_find.getText().toString())){ // содержит ли заданное слово
                    Log.d(MainActivity.LOG_TAG,"содержит слово");
                    if (list.get(i).isPremium()==premium_check){ // премиум или нет
                        Log.d(MainActivity.LOG_TAG,"премиум совпал");
                        if (list.get(i).getCostAd()>=Integer.parseInt(minPrice.getText().toString())&&list.get(i).getCostAd()<=Integer.parseInt(maxPrice.getText().toString())){ // цена
                            Log.d(MainActivity.LOG_TAG,"цена сошлась");
                            if (checkBox_only_with_photo.isChecked()){ // с фото или без
                                Log.d(MainActivity.LOG_TAG,"С ФОТО");
                                if (!list.get(i).getImagePathAd_1().equals(tP)||!list.get(i).getImagePathAd_2().equals(tP)||!list.get(i).getImagePathAd_3().equals(tP)||!list.get(i).getImagePathAd_4().equals(tP)||!list.get(i).getImagePathAd_5().equals(tP)){
                                    Log.d(MainActivity.LOG_TAG,"фото не пустое");
                                    if (list.get(i).getCategoty().equals(spinner_category_find.getSelectedItem().toString())){
                                        Log.d(MainActivity.LOG_TAG,"категория совпала");
                                        if (list.get(i).getCity().contains(spinner_city_find.getSelectedItem().toString())){
                                            Log.d(MainActivity.LOG_TAG,"город совпал");
                                            list_result.add(list.get(i));
                                        }
                                    }
                                }
                            }else{
                                Log.d(MainActivity.LOG_TAG,"БЕЗ ФОТО");
                                if (list.get(i).getCategoty().equals(spinner_category_find.getSelectedItem().toString())){
                                    Log.d(MainActivity.LOG_TAG,"содержит слово");
                                    if (list.get(i).getCity().contains(spinner_city_find.getSelectedItem().toString())){
                                        Log.d(MainActivity.LOG_TAG,"содержит слово");
                                        list_result.add(list.get(i));
                                    }
                                }
                            }
                        }
                    }
                }

            }

            Intent intent = new Intent(Find.this, FindResults.class);
            intent.putExtra("user",user);
            intent.putExtra("results",list_result);
            startActivity(intent);
        }else{
            Snackbar.make(button_find,"Введите запрос", BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    private void init() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        edit_text_find = (EditText) findViewById(R.id.edit_text_find);

        premium = (Button) findViewById(R.id.premium);
        not_premium = (Button) findViewById(R.id.not_premium);
        button_find = (Button) findViewById(R.id.button_find);

        minPrice = (TextView) findViewById(R.id.minPrice);
        maxPrice = (TextView) findViewById(R.id.maxPrice);

        checkBox_only_with_photo = (CheckBox) findViewById(R.id.checkBox_only_with_photo);

        spinner_category_find = (Spinner) findViewById(R.id.spinner_category_find);
        spinner_city_find = (Spinner) findViewById(R.id.spinner_city_find);


        initSeekBar();
        completteCategory_spinner();
        completteCity_spinner();
    }

    private void initSeekBar() {
        rangeSeekBar = (RangeSeekBar) findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                minPrice.setText(minValue.toString());
                maxPrice.setText(maxValue.toString());
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.premium:
                //
                changeBackgroundColorButtons(premium);
                break;
            case R.id.not_premium:
                //
                changeBackgroundColorButtons(not_premium);
                break;
            case R.id.button_find:
                // find logic
                findLogic();
                break;
            default:
                break;
        }
    }

    private void changeBackgroundColorButtons(Button button) {
        if (button.getText().equals(getResources().getString(R.string.premium))){
            // премиум
            premium.setBackgroundColor(getResources().getColor(R.color.green));
            not_premium.setBackgroundColor(getResources().getColor(R.color.blue));
            premium_check = true;
        }else if (button.getText().equals(getResources().getString(R.string.not_premium))){
            // не премиум
            premium.setBackgroundColor(getResources().getColor(R.color.blue));
            not_premium.setBackgroundColor(getResources().getColor(R.color.green));
            premium_check = false;
        }
    }

    private void completteCategory_spinner(){
        ArrayList<String>list_category = new ArrayList<>();
        list_category.add("ВСЕ КАТЕГОРИИ");
        list_category.add("Работа");
        list_category.add("Недвижимость");
        list_category.add("Женский гардероб");
        list_category.add("Мужской гардероб");
        list_category.add("Детский гардероб");
        list_category.add("Детские товары");
        list_category.add("Хендмейд");
        list_category.add("Авто и мото");
        list_category.add("Телефоны и планшеты");
        list_category.add("Фото и видеокамеры");
        list_category.add("Компьютеры");
        list_category.add("Электроника и бытовая техника");
        list_category.add("Для дома для дачи");
        list_category.add("Ремонт и строительство");
        list_category.add("Красота и здоровье");
        list_category.add("Спорт и отдых");
        list_category.add("Хобби и развлечения");
        list_category.add("Животные");
        list_category.add("Для бизнеса");
        list_category.add("Прочее");
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list_category);
        spinner_category_find.setAdapter(adapter);
    }

    private void completteCity_spinner(){
        ArrayList<String> cities = new ArrayList<>();
        cities.add("Абакан");
        cities.add("Азов");
        cities.add("Александров");
        cities.add("Алексин");
        cities.add("Альметьевск");
        cities.add("Анапа");
        cities.add("Ангарск");
        cities.add("Анжеро-Судженск");
        cities.add("Апатиты");
        cities.add("Арзамас");
        cities.add("Армавир");
        cities.add("Арсеньев");
        cities.add("Артем");
        cities.add("Архангельск");
        cities.add("Асбест");
        cities.add("Астрахань");
        cities.add("Ачинск");
        cities.add("Балаково");
        cities.add("Балахна");
        cities.add("Балашиха");
        cities.add("Балашов");
        cities.add("Барнаул");
        cities.add("Батайск");
        cities.add("Белгород");
        cities.add("Белебей");
        cities.add("Белово");
        cities.add("Белогорск (Амурская область)");
        cities.add("Белорецк");
        cities.add("Белореченск");
        cities.add("Бердск");
        cities.add("Березники");
        cities.add("Березовский (Свердловская область)");
        cities.add("Бийск");
        cities.add("Биробиджан");
        cities.add("Благовещенск (Амурская область)");
        cities.add("Бор");
        cities.add("Борисоглебск");
        cities.add("Боровичи");
        cities.add("Братск");
        cities.add("Брянск");
        cities.add("Бугульма");
        cities.add("Буденновск");
        cities.add("Бузулук");
        cities.add("Буйнакск");
        cities.add("Великие Луки");
        cities.add("Великий Новгород");
        cities.add("Верхняя Пышма");
        cities.add("Видное");
        cities.add("Владивосток");
        cities.add("Владикавказ");
        cities.add("Владимир");
        cities.add("Волгоград");
        cities.add("Волгодонск");
        cities.add("Волжск");
        cities.add("Волжский");
        cities.add("Вологда");
        cities.add("Вольск");
        cities.add("Воркута");
        cities.add("Воронеж");
        cities.add("Воскресенск");
        cities.add("Воткинск");
        cities.add("Всеволожск");
        cities.add("Выборг");
        cities.add("Выкса");
        cities.add("Вязьма");
        cities.add("Гатчина");
        cities.add("Геленджик");
        cities.add("Георгиевск");
        cities.add("Глазов");
        cities.add("Горно-Алтайск");
        cities.add("Грозный");
        cities.add("Губкин");
        cities.add("Гудермес");
        cities.add("Гуково");
        cities.add("Гусь-Хрустальный");
        cities.add("Дербент");
        cities.add("Дзержинск");
        cities.add("Димитровград");
        cities.add("Дмитров");
        cities.add("Долгопрудный");
        cities.add("Домодедово");
        cities.add("Донской");
        cities.add("Дубна");
        cities.add("Евпатория");
        cities.add("Егорьевск");
        cities.add("Ейск");
        cities.add("Екатеринбург");
        cities.add("Елабуга");
        cities.add("Елец");
        cities.add("Ессентуки");
        cities.add("Железногорск (Красноярский край)");
        cities.add("Железногорск (Курская область)");
        cities.add("Жигулевск");
        cities.add("Жуковский");
        cities.add("Заречный");
        cities.add("Зеленогорск");
        cities.add("Зеленодольск");
        cities.add("Златоуст");
        cities.add("Иваново");
        cities.add("Ивантеевка");
        cities.add("Ижевск");
        cities.add("Избербаш");
        cities.add("Иркутск");
        cities.add("Искитим");
        cities.add("Ишим");
        cities.add("Ишимбай");
        cities.add("Йошкар-Ола");
        cities.add("Казань");
        cities.add("Калининград");
        cities.add("Калуга");
        cities.add("Каменск-Уральский");
        cities.add("Каменск-Шахтинский");
        cities.add("Камышин");
        cities.add("Канск");
        cities.add("Каспийск");
        cities.add("Кемерово");
        cities.add("Керчь");
        cities.add("Кинешма");
        cities.add("Кириши");
        cities.add("Киров (Кировская область)");
        cities.add("Кирово-Чепецк");
        cities.add("Киселевск");
        cities.add("Кисловодск");
        cities.add("Клин");
        cities.add("Клинцы");
        cities.add("Ковров");
        cities.add("Когалым");
        cities.add("Коломна");
        cities.add("Комсомольск-на-Амуре");
        cities.add("Копейск");
        cities.add("Королев");
        cities.add("Кострома");
        cities.add("Котлас");
        cities.add("Красногорск");
        cities.add("Краснодар");
        cities.add("Краснокаменск");
        cities.add("Краснокамск");
        cities.add("Краснотурьинск");
        cities.add("Красноярск");
        cities.add("Кропоткин");
        cities.add("Крымск");
        cities.add("Кстово");
        cities.add("Кузнецк");
        cities.add("Кумертау");
        cities.add("Кунгур");
        cities.add("Курган");
        cities.add("Курск");
        cities.add("Кызыл");
        cities.add("Лабинск");
        cities.add("Лениногорск");
        cities.add("Ленинск-Кузнецкий");
        cities.add("Лесосибирск");
        cities.add("Липецк");
        cities.add("Лиски");
        cities.add("Лобня");
        cities.add("Лысьва");
        cities.add("Лыткарино");
        cities.add("Люберцы");
        cities.add("Магадан");
        cities.add("Магнитогорск");
        cities.add("Майкоп");
        cities.add("Махачкала");
        cities.add("Междуреченск");
        cities.add("Мелеуз");
        cities.add("Миасс");
        cities.add("Минеральные Воды");
        cities.add("Минусинск");
        cities.add("Михайловка");
        cities.add("Михайловск (Ставропольский край)");
        cities.add("Мичуринск");
        cities.add("Москва");
        cities.add("Мурманск");
        cities.add("Муром");
        cities.add("Мытищи");
        cities.add("Набережные Челны");
        cities.add("Назарово");
        cities.add("Назрань");
        cities.add("Нальчик");
        cities.add("Наро-Фоминск");
        cities.add("Находка");
        cities.add("Невинномысск");
        cities.add("Нерюнгри");
        cities.add("Нефтекамск");
        cities.add("Нефтеюганск");
        cities.add("Нижневартовск");
        cities.add("Нижнекамск");
        cities.add("Нижний Новгород");
        cities.add("Нижний Тагил");
        cities.add("Новоалтайск");
        cities.add("Новокузнецк");
        cities.add("Новокуйбышевск");
        cities.add("Новомосковск");
        cities.add("Новороссийск");
        cities.add("Новосибирск");
        cities.add("Новотроицк");
        cities.add("Новоуральск");
        cities.add("Новочебоксарск");
        cities.add("Новочеркасск");
        cities.add("Новошахтинск");
        cities.add("Новый Уренгой");
        cities.add("Ногинск");
        cities.add("Норильск");
        cities.add("Ноябрьск");
        cities.add("Нягань");
        cities.add("Обнинск");
        cities.add("Одинцово");
        cities.add("Озерск (Челябинская область)");
        cities.add("Октябрьский");
        cities.add("Омск");
        cities.add("Орел");
        cities.add("Оренбург");
        cities.add("Орехово-Зуево");
        cities.add("Орск");
        cities.add("Павлово");
        cities.add("Павловский Посад");
        cities.add("Пенза");
        cities.add("Первоуральск");
        cities.add("Пермь");
        cities.add("Петрозаводск");
        cities.add("Петропавловск-Камчатский");
        cities.add("Подольск");
        cities.add("Полевской");
        cities.add("Прокопьевск");
        cities.add("Прохладный");
        cities.add("Псков");
        cities.add("Пушкино");
        cities.add("Пятигорск");
        cities.add("Раменское");
        cities.add("Ревда");
        cities.add("Реутов");
        cities.add("Ржев");
        cities.add("Рославль");
        cities.add("Россошь");
        cities.add("Ростов-на-Дону");
        cities.add("Рубцовск");
        cities.add("Рыбинск");
        cities.add("Рязань");
        cities.add("Салават");
        cities.add("Сальск");
        cities.add("Самара");
        cities.add("Санкт-Петербург");
        cities.add("Саранск");
        cities.add("Сарапул");
        cities.add("Саратов");
        cities.add("Саров");
        cities.add("Свободный");
        cities.add("Севастополь");
        cities.add("Северодвинск");
        cities.add("Северск");
        cities.add("Сергиев Посад");
        cities.add("Серов");
        cities.add("Серпухов");
        cities.add("Сертолово");
        cities.add("Сибай");
        cities.add("Симферополь");
        cities.add("Славянск-на-Кубани");
        cities.add("Смоленск");
        cities.add("Соликамск");
        cities.add("Солнечногорск");
        cities.add("Сосновый Бор");
        cities.add("Сочи");
        cities.add("Ставрополь");
        cities.add("Старый Оскол");
        cities.add("Стерлитамак");
        cities.add("Ступино");
        cities.add("Сургут");
        cities.add("Сызрань");
        cities.add("Сыктывкар");
        cities.add("Таганрог");
        cities.add("Тамбов");
        cities.add("Тверь");
        cities.add("Тимашевск");
        cities.add("Тихвин");
        cities.add("Тихорецк");
        cities.add("Тобольск");
        cities.add("Тольятти");
        cities.add("Томск");
        cities.add("Троицк");
        cities.add("Туапсе");
        cities.add("Туймазы");
        cities.add("Тула");
        cities.add("Тюмень");
        cities.add("Узловая");
        cities.add("Улан-Удэ");
        cities.add("Ульяновск");
        cities.add("Урус-Мартан");
        cities.add("Усолье-Сибирское");
        cities.add("Уссурийск");
        cities.add("Усть-Илимск");
        cities.add("Уфа");
        cities.add("Ухта");
        cities.add("Феодосия");
        cities.add("Фрязино");
        cities.add("Хабаровск");
        cities.add("Ханты-Мансийск");
        cities.add("Хасавюрт");
        cities.add("Химки");
        cities.add("Чайковский");
        cities.add("Чапаевск");
        cities.add("Чебоксары");
        cities.add("Челябинск");
        cities.add("Черемхово");
        cities.add("Череповец");
        cities.add("Черкесск");
        cities.add("Черногорск");
        cities.add("Чехов");
        cities.add("Чистополь");
        cities.add("Чита");
        cities.add("Шадринск");
        cities.add("Шали");
        cities.add("Шахты");
        cities.add("Шуя");
        cities.add("Щекино");
        cities.add("Щелково");
        cities.add("Электросталь");
        cities.add("Элиста");
        cities.add("Энгельс");
        cities.add("Южно-Сахалинск");
        cities.add("Юрга");
        cities.add("Якутск");
        cities.add("Ялта");
        cities.add("Ярославль");
        ArrayAdapter<String>adapter_cities = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cities);
        spinner_city_find.setAdapter(adapter_cities);
    }
}
