package com.yhs.test;

import java.io.IOException;
import java.util.ArrayList;

import util.ReadStopWords;

public class RemoveStopWord {

	static ArrayList<String> stopWordList = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		String content = "ཞིན་ཧྭ་གསར་འགྱུར་ཁང་/གི་/ལི་ཞོ་རིན་/གྱིས་/པར་/བསྒྲོན།་ /ཞིན་ཧྭ་གསར་འགྱུར་ཁང་/གི་/ལོའུ་ཀྲིན་/གྱིས་/པར་/བསྒྲོན།་ཞིན་ཧྭ་གསར་འགྱུར་ཁང་/གི་/ལོའུ་ཀྲིན་/གྱིས་/པར་/བསྒྲོན།་ 　　/ཟླ་/༡༡་/པའི་/ཚེས་/༣༠་/ཉིན་/ཀྲུང་གོ་/ཀྲུང་དབྱང་/ཆབ་སྲིད་ཅུས་/ཀྱི་/རྒྱུན་ཨུ་/དང་/།་རྒྱལ་/སྲིད་སྤྱི་/ཁྱབ་ཁང་/གི་/སྤྱི་ཁྱབ་/ཧྲུའུ་ཅི་/བཅས་/ཀྱི་/འགན་གཅིག་/ལྕོགས་/སུ་/བཞེས་/པའི་/ལི་ཁེ་ཆིང་/གིས་/རང་རྒྱལ་/གྱི་/འབུམ་རམས་པ་/ཕྱི་མའི་/གཞོན་སྐྱེས་/ཤེས་ལྡན་པའི་/འཐུས་མི་/བགྲོ་/གླེང་ཚོགས་/ཐོག་/ཏུ་/གལ་ཆེའི་/གསུང་བཤད་གནང་/ཡོད་/དེ།་ཚོགས་/ཐོག་/ཏུ་/ཁོས་/ཐོག་མར་/ཀྲུང་གུང་/ཀྲུང་དབྱང་/དང་/རྒྱལ་སྲིད་སྤྱི་ཁྱབ་ཁང་/གི་/ཚབ་བྱས་/ནས་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ལམ་ལུགས་/བཙུགས་/ནས་/ལོ་འཁོར་/༣༠་/ལོན་/པར་/དགའ་སྟོན་/རྟེན་འབྲེལ་/ཞུས་ཞོར།་/ལམ་ལུགས་/འདིས་/བླངས་/པའི་/གཡུར་/དུ་/ཟ་བའི་/གྲུབ་འབྲས་/ལ་/གཟེང་བསྟོད་/བྱས།་/དེར་/མཐུད་/རྒྱལ་ཡོངས་/ཀྱི་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/གཞོན་སྐྱེས་/ཤེས་ལྡན་པ་/རྣམས་/ལ་/ཁམས་བདེ་/དྲིས་/ཤིང་/།་རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ཤེས་ལྡན་མི་སྣས་/སྤྱི་ཚོགས་/ལ་/བྱས་རྗེས་/མང་བོ་/བཞག་/ཡོད་/པར་/སྙིང་ཐག་པ་/ནས་/བཀའ་དྲིན་/ཞུས།་ 　　/ལི་/ཁེ་ཆིང་/གིས་/རྒྱལ་ཡོངས་/ཀྱི་/ཕྱོགས་སོ་སོས་/ལོ་ངོ་/༣༠་/ལྷག་/ལ་/གཙིགས་མཐོང་/ཆེན་པོ་/བྱས་/ནས་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ལམ་ལུགས་/མེད་པ་/ནས་/ཡོད་/པ་/དང་/།་/ཡོད་/པ་/ནས་/འཕེལ་བ།་/འཕེལ་/བ་/ནས་/དར་རྒྱས་/སུ་/སོང་/ཡོད་/པ་/དང་/།་/ལམ་ལུགས་/འདིའི་/མཐུ་/ལ་/བརྟེན་/ནས་/བྱུང་/བའི་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ཤེས་ལྡན་མི་སྣ་/རྣམས་/རྒྱལ་ཁབ་/འཛུགས་སྐྲུན་/གྱི་/བྱ་གཞག་/ཁྲོད་/གཞུང་/ཤིང་/ལྟ་བུར་/གྱུར་/ཡོད་/དེ།་/ཁོ་ཚོ་/དཔལ་འབྱོར་/འཕེལ་རྒྱས་/དང་/སྤྱི་ཚོགས་/ལས་རིགས།་/ཚན་རིག་/ཞིབ་འཇུག་/  /ལས་གཞི་/འཕེལ་རྒྱས་/སོགས་/ཀྱི་/ཁྱབ་ཁོངས་/སོ་སོར་/ཁྱབ་ཅིང་།་བྱས་/རྗེས་/མི་/དམན་/པ་/བཞག་/ཡོད་/ཟེར།་/ 　　/ལི་/ཁེ་ཆིང་/གིས་/རང་རྒྱལ་/ལ་/མི་འབོར་/ཧ་ཅང་/མང་བ་/དང་/།་/མི་འབོར་/མང་བོའི་/ཁྲོད་/དུ་/དགའ་ཁ་/འཁྱེར་/ཕྱོགས་/མི་/འདྲ་/བའི་/མི་/མང་ཕྱིར།་/ལས་རིགས་/སོ་སོའི་/ཤེས་ལྡན་མི་སྣ་/སྐྱེད་སྲིང་བྱེད་/ཐུབ།་/ལོ་/ལྔའི་/འཆར་གཞི་/ཐེངས་/བཅུ་གསུམ་པའི་/དུས་སྐབས་/སུ།་/ཐོན་ཁུངས་/ཀྱི་/གནད་དོན་/དང་/ཁོར་ཡུག་/གི་/བཀག་རྒྱ་/ལས་/གྲོལ་ཏེ།་/ཐོན་སྐྱེད་/ནུས་ཤུགས་/ཀྱི་/ཆུ་ཚད་/ཇེ་མཐོར་/བཏང་/ནས།་/འཛམ་གླིང་/གི་/ཚན་རིག་/གསར་བརྗེ་/དང་/ལས་རིགས་/གསར་བརྗེའི་/ཁྲོད་/སྔོན་ཐོན་/འབྲས་བུ་/བླངས་/རྗེས།་/ཡོང་འབབ་/བར་མའི་/དཀའ་གནས་/ལས་/ཐར་བར་/བྱ་དགོས།་/གོང་/གི་/ལས་ཀའི་/གནད་/འདི་དག་/ལེགས་འགྲུབ་/འབྱུང་བ་/ལ་/ངེས་/པར་/ཤེས་ལྡན་/དུ་/མི་སྣ་/སྐྱེད་སྲིང་བྱས་/ནས་/ལས་རིགས་/གསར་བརྗེ་/ཡོང་/དགོས་/པའི་/བླང་བྱ་/བསྟན།་/ 　　/ལི་/ཁེ་ཆིང་/གིས་/རམ་འབྱམས་/ཕྱི་མའི་/ཤེས་ལྡན་མི་སྣར་/ཆེད་ལས་/མི་/འདྲ་/བའི་/ཤེས་བྱ་/དང་/འཇོན་ཐང་/ཕུན་སུམ་ཚོགས་/པས།་/ཁོ་ཚོའི་/ནུས་པ་/འདོན་སྤེལ་/ཐུབ་/མིན་/ནི་/ལས་རིགས་/གསར་བརྗེ་/ལེགས་འགྲུབ་/ཡོང་/མིན་/གྱི་/གནད་འགག་/ཏུ་/གྱུར་/ཡོད་/ཟེར།་/ཁོས་/ཐོག་མར་/ཐོས་/པ་/སྔོན་/དུ་/བཏིང་/ནས་/ཞིབ་འཇུག་/བྱ་/རྒྱུའི་/ཤེས་བྱ་/ལ་/དཀའ་སྤྱད་འབད་བརྩོན་/གྱིས་/སློབ་སྦྱོང་བྱས་/ནས་/ལྟ་བའི་/མཐོང་/ཡངས་པོར་/ཕྱེས་/དགོས་/པ་/དང་/།་/དེ་/ནས་/ལས་རིགས་/གསར་བཏོད་/ལ་/ལག་ལེན་/གྱིས་/དཔལ་འབྱོར་/འཕེལ་རྒྱས་/དད་/སྤྱི་ཚོགས་/ཐོན་ལས།་/ཁེ་ལས་/གཉེར་བ་/སོགས་/ཀྱི་/བྱེད་ཐབས་/སྤྱད་/ནས་/ཐོན་སྐྱེད་ནུས་ཤུགས་/གོང་འཕེལ་/ཏུ་/གཏོང་/བ་/དང་/།་/སྤྱི་ཚོགས་/ཀྱི་/ལས་ཞུགས་/ཁོད་/ཡངས་པོ་/དང་/ལས་རིགས་/གསར་སྤེལ་/གྱི་/གོ་སྐབས་/མང་བར་/བྱ་དགོས།་/མཐར་/འཛམ་གླིང་/གི་/འཕེལ་ཕྱོཧས་/གཞིར་བཟུང་/ནས་/རྒྱལ་སྤྱིའི་/སྤྱི་ཚོགས་/ཀྱི་/འཕེལ་རྒྱས་/དང་/ཁ་འཕྲོད་/པ་/བྱས་/ནས་/དེའི་/འགྲན་རྩོད་/ལས་/རྣམ་པར་/རྒྱལ་/བའི་/སྤོབས་སེམས་/བསྐྱེད་/དགོས་/པའི་/རེ་བ་/བཏོན།་ 　　/ལི་ཁེ་ཆིང་/གིས་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ལམ་ལུགས་/ནི་/བཅོས་བསྒྱུར་/སྒོ་འབྱེད་/ཀྱི་/རྗེས་སོར་/བྱུང་/བ་/ཡིན་/པ་/དང་/།་/དེས་/བཅོས་བསྒྱུར་/སྒོ་འབྱེད་/ཁྲོད་/ནུས་པ་/གང་ལེགས་/འདོན་/ཐབས་བྱ་/དགོས།་/དེར་/མ་ཟད་/གོམ་གང་མདུན་སྤོས་/ཀྱིས་/བསམ་བློ་/བཅིངས་འགྲོལ་/དང་/བློ་ཁ་/ཡངས་པོ་/ཕྱེ་/ནས་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ཤེས་ལྡན་མི་སྣ་/སྐྱེད་སྲིང་བྱ་/རྒྱུ་/དམིགས་ཡུལ་/དང་/གནད་དུ་/འཛིན་དགོས།་/ལམ་ལུགས་/འདིས་/ཤེས་ལྡན་མི་སྣ་/ལ་/བརྩི་/མཐོང་/།་/གསར་བཏོད་/ལ་/མཐོང་ཆེན་/དང་/སྐུལ་མ་/བྱེད་/པའི་/བསམ་བློའི་/གུ་ཡངས་པོ་/ཡོང་/བའི་/ལས་འགན་/འགྲུབ་/ཐུབ་/པ་/བྱ་/དགོས་/པར་/བསྟན།་ 　　/ཁ་བྱང་ཕྱི་མོ།་ལི་ཁེ་ཆིང་/གིས་/གཞོན་སྐྱེས་/ཤེས་ལྡན་པ་/སྐྱོང་/རྒྱུར་/བརྩོན་དགོས་/ཟེར།་ /(/རྩོམ་/འགན་པ།་དབང་འདུས།་)/ཞིན་ཧྭ་གསར་འགྱུར་ཁང་/གི་/ལི་ཞོ་རིན་/གྱིས་/པར་/བསྒྲོན།་ /ཞིན་ཧྭ་གསར་འགྱུར་ཁང་/གི་/ལོའུ་ཀྲིན་/གྱིས་/པར་/བསྒྲོན།་ཞིན་ཧྭ་གསར་འགྱུར་ཁང་/གི་/ལོའུ་ཀྲིན་/གྱིས་/པར་/བསྒྲོན།་ 　　/ཟླ་/༡༡་/པའི་/ཚེས་/༣༠་/ཉིན་/ཀྲུང་གོ་/ཀྲུང་དབྱང་/ཆབ་སྲིད་ཅུས་/ཀྱི་/རྒྱུན་ཨུ་/དང་/།་རྒྱལ་/སྲིད་སྤྱི་/ཁྱབ་ཁང་/གི་/སྤྱི་ཁྱབ་/ཧྲུའུ་ཅི་/བཅས་/ཀྱི་/འགན་གཅིག་/ལྕོགས་/སུ་/བཞེས་/པའི་/ལི་ཁེ་ཆིང་/གིས་/རང་རྒྱལ་/གྱི་/འབུམ་རམས་པ་/ཕྱི་མའི་/གཞོན་སྐྱེས་/ཤེས་ལྡན་པའི་/འཐུས་མི་/བགྲོ་/གླེང་ཚོགས་/ཐོག་/ཏུ་/གལ་ཆེའི་/གསུང་བཤད་གནང་/ཡོད་/དེ།་ཚོགས་/ཐོག་/ཏུ་/ཁོས་/ཐོག་མར་/ཀྲུང་གུང་/ཀྲུང་དབྱང་/དང་/རྒྱལ་སྲིད་སྤྱི་ཁྱབ་ཁང་/གི་/ཚབ་བྱས་/ནས་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ལམ་ལུགས་/བཙུགས་/ནས་/ལོ་འཁོར་/༣༠་/ལོན་/པར་/དགའ་སྟོན་/རྟེན་འབྲེལ་/ཞུས་ཞོར།་/ལམ་ལུགས་/འདིས་/བླངས་/པའི་/གཡུར་/དུ་/ཟ་བའི་/གྲུབ་འབྲས་/ལ་/གཟེང་བསྟོད་/བྱས།་/དེར་/མཐུད་/རྒྱལ་ཡོངས་/ཀྱི་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/གཞོན་སྐྱེས་/ཤེས་ལྡན་པ་/རྣམས་/ལ་/ཁམས་བདེ་/དྲིས་/ཤིང་/།་རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ཤེས་ལྡན་མི་སྣས་/སྤྱི་ཚོགས་/ལ་/བྱས་རྗེས་/མང་བོ་/བཞག་/ཡོད་/པར་/སྙིང་ཐག་པ་/ནས་/བཀའ་དྲིན་/ཞུས།་ 　　/ལི་/ཁེ་ཆིང་/གིས་/རྒྱལ་ཡོངས་/ཀྱི་/ཕྱོགས་སོ་སོས་/ལོ་ངོ་/༣༠་/ལྷག་/ལ་/གཙིགས་མཐོང་/ཆེན་པོ་/བྱས་/ནས་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ལམ་ལུགས་/མེད་པ་/ནས་/ཡོད་/པ་/དང་/།་/ཡོད་/པ་/ནས་/འཕེལ་བ།་/འཕེལ་/བ་/ནས་/དར་རྒྱས་/སུ་/སོང་/ཡོད་/པ་/དང་/།་/ལམ་ལུགས་/འདིའི་/མཐུ་/ལ་/བརྟེན་/ནས་/བྱུང་/བའི་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ཤེས་ལྡན་མི་སྣ་/རྣམས་/རྒྱལ་ཁབ་/འཛུགས་སྐྲུན་/གྱི་/བྱ་གཞག་/ཁྲོད་/གཞུང་/ཤིང་/ལྟ་བུར་/གྱུར་/ཡོད་/དེ།་/ཁོ་ཚོ་/དཔལ་འབྱོར་/འཕེལ་རྒྱས་/དང་/སྤྱི་ཚོགས་/ལས་རིགས།་/ཚན་རིག་/ཞིབ་འཇུག་/  /ལས་གཞི་/འཕེལ་རྒྱས་/སོགས་/ཀྱི་/ཁྱབ་ཁོངས་/སོ་སོར་/ཁྱབ་ཅིང་།་བྱས་/རྗེས་/མི་/དམན་/པ་/བཞག་/ཡོད་/ཟེར།་/ 　　/ལི་/ཁེ་ཆིང་/གིས་/རང་རྒྱལ་/ལ་/མི་འབོར་/ཧ་ཅང་/མང་བ་/དང་/།་/མི་འབོར་/མང་བོའི་/ཁྲོད་/དུ་/དགའ་ཁ་/འཁྱེར་/ཕྱོགས་/མི་/འདྲ་/བའི་/མི་/མང་ཕྱིར།་/ལས་རིགས་/སོ་སོའི་/ཤེས་ལྡན་མི་སྣ་/སྐྱེད་སྲིང་བྱེད་/ཐུབ།་/ལོ་/ལྔའི་/འཆར་གཞི་/ཐེངས་/བཅུ་གསུམ་པའི་/དུས་སྐབས་/སུ།་/ཐོན་ཁུངས་/ཀྱི་/གནད་དོན་/དང་/ཁོར་ཡུག་/གི་/བཀག་རྒྱ་/ལས་/གྲོལ་ཏེ།་/ཐོན་སྐྱེད་/ནུས་ཤུགས་/ཀྱི་/ཆུ་ཚད་/ཇེ་མཐོར་/བཏང་/ནས།་/འཛམ་གླིང་/གི་/ཚན་རིག་/གསར་བརྗེ་/དང་/ལས་རིགས་/གསར་བརྗེའི་/ཁྲོད་/སྔོན་ཐོན་/འབྲས་བུ་/བླངས་/རྗེས།་/ཡོང་འབབ་/བར་མའི་/དཀའ་གནས་/ལས་/ཐར་བར་/བྱ་དགོས།་/གོང་/གི་/ལས་ཀའི་/གནད་/འདི་དག་/ལེགས་འགྲུབ་/འབྱུང་བ་/ལ་/ངེས་/པར་/ཤེས་ལྡན་/དུ་/མི་སྣ་/སྐྱེད་སྲིང་བྱས་/ནས་/ལས་རིགས་/གསར་བརྗེ་/ཡོང་/དགོས་/པའི་/བླང་བྱ་/བསྟན།་/ 　　/ལི་/ཁེ་ཆིང་/གིས་/རམ་འབྱམས་/ཕྱི་མའི་/ཤེས་ལྡན་མི་སྣར་/ཆེད་ལས་/མི་/འདྲ་/བའི་/ཤེས་བྱ་/དང་/འཇོན་ཐང་/ཕུན་སུམ་ཚོགས་/པས།་/ཁོ་ཚོའི་/ནུས་པ་/འདོན་སྤེལ་/ཐུབ་/མིན་/ནི་/ལས་རིགས་/གསར་བརྗེ་/ལེགས་འགྲུབ་/ཡོང་/མིན་/གྱི་/གནད་འགག་/ཏུ་/གྱུར་/ཡོད་/ཟེར།་/ཁོས་/ཐོག་མར་/ཐོས་/པ་/སྔོན་/དུ་/བཏིང་/ནས་/ཞིབ་འཇུག་/བྱ་/རྒྱུའི་/ཤེས་བྱ་/ལ་/དཀའ་སྤྱད་འབད་བརྩོན་/གྱིས་/སློབ་སྦྱོང་བྱས་/ནས་/ལྟ་བའི་/མཐོང་/ཡངས་པོར་/ཕྱེས་/དགོས་/པ་/དང་/།་/དེ་/ནས་/ལས་རིགས་/གསར་བཏོད་/ལ་/ལག་ལེན་/གྱིས་/དཔལ་འབྱོར་/འཕེལ་རྒྱས་/དད་/སྤྱི་ཚོགས་/ཐོན་ལས།་/ཁེ་ལས་/གཉེར་བ་/སོགས་/ཀྱི་/བྱེད་ཐབས་/སྤྱད་/ནས་/ཐོན་སྐྱེད་ནུས་ཤུགས་/གོང་འཕེལ་/ཏུ་/གཏོང་/བ་/དང་/།་/སྤྱི་ཚོགས་/ཀྱི་/ལས་ཞུགས་/ཁོད་/ཡངས་པོ་/དང་/ལས་རིགས་/གསར་སྤེལ་/གྱི་/གོ་སྐབས་/མང་བར་/བྱ་དགོས།་/མཐར་/འཛམ་གླིང་/གི་/འཕེལ་ཕྱོཧས་/གཞིར་བཟུང་/ནས་/རྒྱལ་སྤྱིའི་/སྤྱི་ཚོགས་/ཀྱི་/འཕེལ་རྒྱས་/དང་/ཁ་འཕྲོད་/པ་/བྱས་/ནས་/དེའི་/འགྲན་རྩོད་/ལས་/རྣམ་པར་/རྒྱལ་/བའི་/སྤོབས་སེམས་/བསྐྱེད་/དགོས་/པའི་/རེ་བ་/བཏོན།་ 　　/ལི་ཁེ་ཆིང་/གིས་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ལམ་ལུགས་/ནི་/བཅོས་བསྒྱུར་/སྒོ་འབྱེད་/ཀྱི་/རྗེས་སོར་/བྱུང་/བ་/ཡིན་/པ་/དང་/།་/དེས་/བཅོས་བསྒྱུར་/སྒོ་འབྱེད་/ཁྲོད་/ནུས་པ་/གང་ལེགས་/འདོན་/ཐབས་བྱ་/དགོས།་/དེར་/མ་ཟད་/གོམ་གང་མདུན་སྤོས་/ཀྱིས་/བསམ་བློ་/བཅིངས་འགྲོལ་/དང་/བློ་ཁ་/ཡངས་པོ་/ཕྱེ་/ནས་/རམ་/འབྱམས་/པ་/ཕྱི་མའི་/ཤེས་ལྡན་མི་སྣ་/སྐྱེད་སྲིང་བྱ་/རྒྱུ་/དམིགས་ཡུལ་/དང་/གནད་དུ་/འཛིན་དགོས།་/ལམ་ལུགས་/འདིས་/ཤེས་ལྡན་མི་སྣ་/ལ་/བརྩི་/མཐོང་/།་/གསར་བཏོད་/ལ་/མཐོང་ཆེན་/དང་/སྐུལ་མ་/བྱེད་/པའི་/བསམ་བློའི་/གུ་ཡངས་པོ་/ཡོང་/བའི་/ལས་འགན་/འགྲུབ་/ཐུབ་/པ་/བྱ་/དགོས་/པར་/བསྟན།་ 　　/ཁ་བྱང་ཕྱི་མོ།་ལི་ཁེ་ཆིང་/གིས་/གཞོན་སྐྱེས་/ཤེས་ལྡན་པ་/སྐྱོང་/རྒྱུར་/བརྩོན་དགོས་/ཟེར།་ /(/རྩོམ་/འགན་པ།་དབང་འདུས།་/";
		System.out.println("content: " + content);
		ReadStopWords rd = new ReadStopWords();
		rd.readStopWords();
		stopWordList = rd.getStopWordList();
//		content.split("/");
		for(int i = 0;i < stopWordList.size();i++){
			content = content.replaceAll(stopWordList.get(i), "");
		}
		System.out.println("content: " + content);
		
		String[] arr = "ཞིན་ཧྭ་གསར་འགྱུར་ཁང་/གི་/ལི་ཞོ་རིན་/གྱིས་//བསྒྲོན".split("/");
		System.out.println("size: " + arr.length);
		for(int i = 0;i < arr.length;i++){
			if(!arr[i].equals("")){
//				System.out.println(arr[i]);
				
			}
		}
		
		
	}
}