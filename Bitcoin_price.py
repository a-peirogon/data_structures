class node:
    def __init__(self, data = None, next=None, prev=None):
        self.data = data
        self.next = next
        self.prev = prev

# Creamos la clase linked_list
class linked_list: 
    def __init__(self):
        self.head = None
    
    def is_empty(self):
        return self.head == None
    
    def there_is_one(self):
        if self.head == None:
            return False
        else:
            flag = ((self.head.next == None) and (self.head.prev == None)) or ((self.head.next == self.head) and (self.head.prev == self.head)) 
            return flag
    
    def add_at_front(self, data):
        if self.is_empty():
            self.head = node(data=data, next=None, prev=None)
        elif self.there_is_one():
            self.head = node(data=data, next=self.head, prev=self.head)
            self.head.prev.next = self.head
            self.head.prev.prev = self.head                        
        else:
            self.head = node(data=data, next=self.head, prev=self.head.prev)
            self.prev.next = self.head
            self.next.prev = self.head

    def add_at_end(self, data):
        if self.is_empty():
            self.head = node(data=data, next=None, prev=None)
        elif self.there_is_one():
            #self.head.data = None
            self.head.next = node(data=data, next=self.head, prev=self.head)
            self.head.prev = self.head.next
        else:
            self.head.prev.next = node(data=data, next=self.head, prev=self.head.prev)
            self.head.prev = self.head.prev.next 
            
    def delete_first_node(self):
        if self.is_empty():
            return
        elif self.there_is_one():
            temp = self.head
            self.head.data = None
            self.head.next = None
            self.head.prev = None
            self.head = None
            return temp.data
        else:
            self.head.next.prev = self.head.prev
            self.head.prev.next = self.head.next
            temp = self.head
            self.head = self.head.next
            if self.there_is_one():
                self.head.next = None
                self.head.prev = None
            return temp.data

    def delete_last_node(self):
        if self.is_empty():
            return
        if self.there_is_one():
            temp = str(self.head.data)
            self.head.data = None
            self.head.next = None
            self.head.prev = None
            self.head = None
            return temp
        else:
            temp = self.head.prev
            self.head.prev = self.head.prev.prev
            self.head.prev.next = self.head
            if self.there_is_one():
                self.head.next = None
                self.head.prev = None
            return temp.data
    
    def print_list(self):
        if self.is_empty():
            return
        elif self.there_is_one():
            print(self.head.data, end='')
        else:
            node = self.head
            stop = self.head.prev
            while node != stop:
                print(node.data, end=' ')
                node = node.next
            print(node.data, end='')
            
    def count_nodes(self):
        count = 0
        node = self.head
        while self.head.prev != node:
            node = node.next
            count += 1
        if self.head.next == self.head.prev:
            count += 1
        return count
    
    def count_days(self, large):
        count = 0
        #large = self.count_nodes()
        node = self.head.next
        flag = True
        while count < large:
            if node.data > self.head.data:
                flag = False
                count += 1
                break
            count += 1
            node = node.next
        if flag:
            count = 0
        return count
                           
##########################################################
            
m = input()

s = linked_list()

for i in m.split():
    s.add_at_end(int(i))

t = linked_list()
large = s.count_nodes()

while not s.there_is_one():
    days = s.count_days(large)
    t.add_at_end(days)
    s.delete_first_node()
    large -= 1

t.add_at_end(0)

t.print_list()

