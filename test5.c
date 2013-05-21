#include <stdio.h>
#include <stdlib.h> 

typedef struct _node
{
	int key;
	struct _node *prev;
	struct _node *next;
} Queue;

Queue *head, *tail;

void init_queue(void) // ť �ʱ�ȭ
{
	head = (node*)malloc(sizeof(node));
	tail = (node*)malloc(sizeof(node));

	head->prev = head;
	head->next = tail;
	tail->prev = head;
	tail->next = tail;
}

void put(int k)   // put
{
	node *t;

	t = (node*)malloc(sizeof(node));
	t->key = k;

	tail->prev->next = t;
	t->prev = tail->prev;
	tail->prev = t;
	t->next = tail;
}

void print_queue(void) // ���
{
	node *now;
	
	for(now = head->next; now != tail ; now = now->next)
	{
		if( now->key == '+' || now->key == '-' || now->key == '*' || now->key == '/' )
		{        // ���� ť�� ����ִ� ���� ���ڶ��
			printf("%c ", now->key); // ����(%c)�� ���
		}
		else       // ���ڶ��
			printf("%d ", now->key); // ����(%d)�� ���
	} 
}

void calc_prefix(void)
{
	int a,b,c,d;
	int t;

	node *now;

	now=head->next;

	for(t=0;t<9;t++)
	{
		if(((now->data == '+') || (now->data == '-') || (now->data == '*') || (now->data == '/')) && !( (now->next->key == '+') || (now->next->key == '-') || (now->next->key == '*') || (now->next->key == '/') )
			&& !( (now->next->next->key == '+') || (now->next->next->key == '-') || (now->next->next->key == '*') || (now->next->key == '/') ) )
		{ // <���� ���, ���� ���, �ٴ��� ���>�� ���� <������, ����, ����> ��, prefix������ �� �� �ִ� ���·� �Ǿ��ִٸ�
			d = now->key;    // �����ڸ� d�� �ְ�
			a = now->next->key;   //
			b = now->next->next->key; // ���ڸ� a�� b�� �־�

			if(d == '+')
			{
				c=a+b;     // �����ڿ� ���� ������ �ؼ� ���� c�� �ְ�
				now->key=c;    // �����ڰ� ����ִ� �ڸ��� �� ���� �ִ´�
			}
			else if(d == '-')
			{
				c=a-b;
				now->key=c;
			}
			else if(d == '*')
			{
				c=a*b;
				now->key=c;
			}
			else if(d == '/')
			{
				c=a/b;
				now->key=c;
			}
			now->next = now->next->next->next;  // ���� ����� ������
			now->next->next->next->prev = now;  // ���ڰ� ����ִ� ����� �������� �����Ѵ�
			now=now->next;       // ��� �˾ƺ��� ���� ���� ���� �̵�
		}
		else
		{
			now=now->next;       // prefix������ �� �� ���ٸ� �ٷ� ���� ���� �̵�
		}
	}
}

int main(void) 
{ 
	init_queue();

	put('-');put('+');put('*');put(9);put('+');put(2);put(8);put('*');put('+');put(4);put(8);put(6);put(3);

	printf("queue�� ó�� ����ϸ� : ");
	print_queue(); 
	printf("\n");

	calc_prefix(); 
	printf("queue�� �ٽ� ����ϸ� : ");
	print_queue();
	printf("\n");
	
	return 0;
}