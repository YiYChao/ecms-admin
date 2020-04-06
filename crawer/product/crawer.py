from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import pymysql
import time
import random


class Product:

    def __init__(self, url, username, password):
        self.__url = url
        self.__username = username
        self.__password = password

        self.__browser = webdriver.Chrome()
        # 定义显示等待
        self.__wait = WebDriverWait(self.__browser, 20)
        # 定义数据库连接
        self.__conn = pymysql.Connect(host='127.0.0.1', user='root', password="Chao2134", database='xcck', port=3306,
                               charset='utf8', cursorclass=pymysql.cursors.DictCursor)

    def __del__(self):
        self.__browser.close()

    def login(self):
        # 打开网页
        self.__browser.get(self.__url)
        # self.__browser.req
        # 找到账号登录登录按钮并点击
        self.__wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, 'ul.formtab li:nth-child(2)'))).click()

        # 找到账号输入框
        username = self.__wait.until(EC.presence_of_element_located((By.ID, 'userName')))
        username.send_keys(self.__username)   # 输入密码
        # 找到密码输入框
        password = self.__wait.until(EC.presence_of_element_located((By.ID, 'password')))
        password.send_keys(self.__password)

        # 找到登录按钮并点击
        self.__wait.until(EC.element_to_be_clickable((By.ID, 'loginButton'))).click()

    def to_pdt_lib(self):
        # 找到商品管理栏目并点击
        self.__wait.until(EC.element_to_be_clickable((By.NAME, 'MCMP1'))).click()
        # 找到我的商品库栏目并点击进入
        self.__wait.until(EC.element_to_be_clickable((By.NAME, 'sop_menu_left_MCMP1Z02'))).click()

    def to_next(self):
        self.__wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, 'a[class="next"]'))).click()

    def get_total(self):
        """
        获得商品库的总页数
        :return: 整型的总页数
        """
        page_txt = self.__wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, 'div[class="pages r"]'))).text
        # 获得分页的div
        list1 = page_txt.split('\n')[::-1]
        for i in list1:
            if i.isdigit():
                return int(i)

    def to_certain(self, page=0):
        # 找到页码输入框
        input_page = self.__wait.until(EC.presence_of_element_located((By.ID, 'inputPageNum')))
        # 输入页码
        input_page.send_keys(page)
        # 跳转到指定的页面
        self.__wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, 'input[value="跳转"]'))).click()

    def get_content(self):
        content = self.__browser.find_elements_by_class_name('popWinContent')
        return content[0].get_attribute("innerHTML")

    def insert(self, page):
        with self.__conn.cursor() as cursor:
            try:
                res = cursor.execute('insert into tb_libpage(content) values (%s)', (page,))
                if res == 1:
                    print('新增成功！', end='\t')
                self.__conn.commit()
            except pymysql.MySQLError as error:
                print(error)
                self.__conn.rollback()  # 遇到异常则进行回滚操作

    def start(self, base_page=0, total_page=1):

        self.login()    # 登录工作台

        time.sleep(40)
        self.to_pdt_lib()   # 去到商品库

        if not base_page == 0:
            self.to_certain(base_page)   # 到指定的页面

        self.insert(self.get_content())     # 新增当前记录
        print('当前页数：', 1 + base_page)

        if total_page == -1:
            total_page = self.get_total()

        for i in range(base_page + 1, total_page):
            self.to_next()
            time.sleep(random.randint(3, 8))
            self.insert(self.get_content())
            print('当前页数：', i + 1)


if __name__ == '__main__':
    product = Product('https://mpassport.suning.com/ids/login', 'XCCK01', 'xcck55555')
    product.start(base_page=0, total_page=100)
